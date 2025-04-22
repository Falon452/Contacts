package com.activecampaign.contacts.domain.usecase

import com.activecampaign.contacts.domain.model.Contact
import com.activecampaign.contacts.domain.model.ContactField
import com.activecampaign.contacts.domain.model.Order
import com.activecampaign.contacts.domain.repository.ContactsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GetContactsUseCaseTest {

    private val mockContactsRepository = mockk<ContactsRepository>()
    private val getContactsUseCase = GetContactsUseCase(mockContactsRepository)

    @Test
    fun `WHEN execute is called, THEN it returns a successful result with list of contacts`() = runTest {
        val expectedContacts = listOf(
            Contact.FullNameContact(id = "1", firstName = "Alice", lastName = "Zephyr"),
            Contact.EmailContact(id = "2", email = "bob@example.com")
        )

        coEvery {
            mockContactsRepository.getContacts(
                ordering = mapOf(
                    ContactField.FIRST_NAME to Order.DESCENDING,
                    ContactField.LAST_NAME to Order.DESCENDING,
                    ContactField.EMAIL to Order.DESCENDING
                ),
                limit = 50
            )
        } returns Result.success(expectedContacts)

        val result = getContactsUseCase.execute()

        assertTrue(result.isSuccess)
        assertEquals(expectedContacts, result.getOrNull())
    }

    @Test
    fun `WHEN execute is called, AND repository returns failure, THEN it returns a failure result`() = runTest {
        val exception = RuntimeException("Repository error")

        coEvery {
            mockContactsRepository.getContacts(
                ordering = mapOf(
                    ContactField.FIRST_NAME to Order.DESCENDING,
                    ContactField.LAST_NAME to Order.DESCENDING,
                    ContactField.EMAIL to Order.DESCENDING
                ),
                limit = 50
            )
        } returns Result.failure(exception)

        val result = getContactsUseCase.execute()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
