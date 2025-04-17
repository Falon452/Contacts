package com.activecampaign.contacs.presentation.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.activecampaign.contacs.presentation.mapper.ContactsViewStateMapper
import com.activecampaign.contacs.presentation.model.ContactsState
import com.activecampaign.contacs.presentation.model.ContactsViewState
import com.activecampaign.contacts.domain.usecase.GetContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    getContactsUseCase: GetContactsUseCase,
    viewStateMapper: ContactsViewStateMapper,
) : ViewModel() {

    private val _state = MutableStateFlow(ContactsState(null))
    @VisibleForTesting
    val state get() = _state.value
    val viewState: StateFlow<ContactsViewState> =
        _state
            .map(viewStateMapper::from)
            .flowOn(Dispatchers.Default)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(0),
                initialValue = viewStateMapper.from(_state.value)
            )

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    contacts = getContactsUseCase.execute(),
                )
            }
        }
    }
}
