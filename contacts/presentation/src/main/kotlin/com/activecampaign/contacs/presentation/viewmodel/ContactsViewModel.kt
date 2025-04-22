package com.activecampaign.contacs.presentation.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.activecampaign.contacs.presentation.di.DefaultDispatcher
import com.activecampaign.contacs.presentation.ext.executeWithRetry
import com.activecampaign.contacs.presentation.mapper.ContactsViewStateMapper
import com.activecampaign.contacs.presentation.model.ContactsEvent
import com.activecampaign.contacs.presentation.model.ContactsEvent.ShowToast
import com.activecampaign.contacs.presentation.model.ContactsState
import com.activecampaign.contacs.presentation.model.ContactsViewState
import com.activecampaign.contacts.domain.usecase.GetContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    viewStateMapper: ContactsViewStateMapper,
) : ViewModel() {

    private val _state = MutableStateFlow(
        ContactsState(
            contacts = emptyList(),
            failedToGetContacts = false,
            isLoading = true,
        )
    )

    @VisibleForTesting
    val state get() = _state.value
    val viewState: StateFlow<ContactsViewState> =
        _state
            .map(viewStateMapper::from)
            .flowOn(defaultDispatcher)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(0),
                initialValue = viewStateMapper.from(_state.value)
            )

    private val _events = Channel<ContactsEvent>()
    val events: Flow<ContactsEvent> = _events.receiveAsFlow()

    init {
        loadContacts()
    }

    private fun loadContacts() {
        viewModelScope.launch(defaultDispatcher) {
            _state.update {
                it.copy(
                    isLoading = true,
                    failedToGetContacts = false,
                )
            }
            getContactsUseCase::execute.executeWithRetry(
                maxAttempts = 5,
                onRetry = { _, _ ->
                    delay(REQUEST_DELAY_MS)
                },
            )
                .onSuccess { contacts ->
                    _state.update {
                        it.copy(
                            contacts = contacts,
                            isLoading = false,
                        )
                    }
                }
                .onFailure { exception ->
                    _events.send(ShowToast(exception.message.orEmpty()))
                    _state.update {
                        it.copy(
                            contacts = emptyList(),
                            failedToGetContacts = true,
                            isLoading = false,
                        )
                    }
                }
        }
    }

    fun onEvent(event: ContactsEvent, showToast: (message: String) -> Unit) {
        when (event) {
            is ShowToast -> showToast(event.message)
        }
    }

    fun onRefresh() {
        loadContacts()
    }

    private companion object {

        const val REQUEST_DELAY_MS = 1000L
    }
}
