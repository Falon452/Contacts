package com.activecampaign.contacts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.activecampaign.contacts.di.IoDispatcher
import com.activecampaign.contacts.model.MainState
import com.activecampaign.theme.ThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val themePreferences: ThemePreferences,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(MainState(isDarkMode = true))
    val state = _state.asStateFlow()

    init {
        observeDarkMode()
    }

    private fun observeDarkMode() {
        viewModelScope.launch(ioDispatcher) {
            themePreferences.observeIsDarkMode()
                .collectLatest { isDarkMode ->
                    _state.update { it.copy(isDarkMode = isDarkMode) }
                }
        }
    }

    fun onToggleTheme(checked: Boolean) {
        viewModelScope.launch(ioDispatcher) {
            themePreferences.saveDarkMode(checked)
        }
    }
}
