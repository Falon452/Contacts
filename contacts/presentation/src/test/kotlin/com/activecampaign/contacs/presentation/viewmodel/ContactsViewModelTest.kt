package com.activecampaign.contacs.presentation.viewmodel

import com.activecampaign.contacs.presentation.mapper.ContactsViewStateMapper
import com.activecampaign.contacs.presentation.model.ContactsEvent
import com.activecampaign.contacs.presentation.model.ContactsEvent.ShowToast
import com.activecampaign.contacs.presentation.model.ContactsViewState
import com.activecampaign.contacts.domain.model.Contact
import com.activecampaign.contacts.domain.usecase.GetContactsUseCase
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@FlowPreview
class ContactsViewModelTest {

    private val mockGetContactsUseCase = mockk<GetContactsUseCase>()
    private val mockViewStateMapper = mockk<ContactsViewStateMapper>()
    private val testDispatcher = StandardTestDispatcher()
    private val testContacts = listOf(
        Contact.EmailContact(id = "x", "john@example.com"),
        Contact.FullNameContact("2", "Jane", "Smith")
    )
    private val viewState = mockk<ContactsViewState>(relaxed = true)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { mockViewStateMapper.from(any()) } returns viewState
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    private fun createViewModel(): ContactsViewModel {
        return ContactsViewModel(
            getContactsUseCase = mockGetContactsUseCase,
            defaultDispatcher = testDispatcher,
            viewStateMapper = mockViewStateMapper
        )
    }

    @Test
    fun `WHEN ViewModel is initialized THEN contacts are loaded`() = runTest {
        coEvery { mockGetContactsUseCase.execute() } returns Result.success(testContacts)

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals(testContacts, viewModel.state.contacts)
        assertFalse(viewModel.state.isLoading)
        assertFalse(viewModel.state.failedToGetContacts)
    }

    @Test
    fun `WHEN contacts loading fails THEN emits ShowToast event and sets failure state`() = runTest {
        val message = "Failed to fetch contacts"
        coEvery { mockGetContactsUseCase.execute() } returns Result.failure(RuntimeException(message))

        val viewModel = createViewModel()
        val events = mutableListOf<ContactsEvent>()
        val job = launch {
            viewModel.events.toList(events)
        }

        advanceUntilIdle()

        assertTrue(viewModel.state.failedToGetContacts)
        assertTrue(viewModel.state.contacts.isEmpty())
        assertFalse(viewModel.state.isLoading)
        assertTrue(events.contains(ShowToast(message)))
        job.cancel()
    }

    @Test
    fun `WHEN onRefresh is called THEN reloads contacts`() = runTest {
        coEvery { mockGetContactsUseCase.execute() } returns Result.success(testContacts)

        val viewModel = createViewModel()

        viewModel.onRefresh()
        advanceUntilIdle()

        coVerify(exactly = 2) { mockGetContactsUseCase.execute() }
    }

    @Test
    fun `viewState is mapped from state`() = runTest {
        coEvery { mockGetContactsUseCase.execute() } returns Result.success(testContacts)

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals(viewState, viewModel.viewState.value)
    }
}
