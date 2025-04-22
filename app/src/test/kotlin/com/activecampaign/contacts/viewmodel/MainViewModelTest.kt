package com.activecampaign.contacts.viewmodel

import com.activecampaign.theme.ThemePreferences
import io.mockk.*
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val themePreferences = mockk<ThemePreferences>(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MainViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN ViewModel is initialized THEN observes theme preferences and updates state`() = runTest {
        val isDarkModeFlow = MutableStateFlow(false)
        coEvery { themePreferences.observeIsDarkMode() } returns isDarkModeFlow

        viewModel = MainViewModel(themePreferences, testDispatcher)
        advanceUntilIdle()

        assertEquals(false, viewModel.state.value.isDarkMode)

        isDarkModeFlow.value = true
        advanceUntilIdle()

        assertEquals(true, viewModel.state.value.isDarkMode)
    }

    @Test
    fun `WHEN onToggleTheme is called THEN saves preference`() = runTest {
        coEvery { themePreferences.observeIsDarkMode() } returns MutableStateFlow(true)

        viewModel = MainViewModel(themePreferences, testDispatcher)
        advanceUntilIdle()

        viewModel.onToggleTheme(false)
        advanceUntilIdle()

        coVerify(exactly = 1) { themePreferences.saveDarkMode(false) }
    }
}
