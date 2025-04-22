package com.activecampaign.contacs.presentation.mapper

import com.activecampaign.contacs.presentation.model.ContactsContent
import com.activecampaign.contacs.presentation.model.ContactsState
import com.activecampaign.contacs.presentation.model.ContactsViewState
import com.activecampaign.contacs.presentation.model.ContactItem
import com.activecampaign.contacts.domain.model.Contact
import org.junit.jupiter.api.Test
import io.mockk.*
import kotlin.test.assertEquals

class ContactsViewStateMapperTest {

    private val mockContactItemMapper = mockk<ContactItemMapper>()
    private val mapper = ContactsViewStateMapper(mockContactItemMapper)

    @Test
    fun `WHEN from is called with loading state, THEN it returns a view state with spinner visible`() {
        val contactsState = ContactsState(
            isLoading = true,
            contacts = listOf(),
            failedToGetContacts = false
        )

        val viewState = mapper.from(contactsState)

        val expectedViewState = ContactsViewState(
            isSpinnerVisible = true,
            contactItemRows = listOf(),
            contactsContent = ContactsContent.CONTACTS
        )
        assertEquals(expectedViewState, viewState)
    }

    @Test
    fun `WHEN from is called with failed state, THEN it returns a view state with error content`() {
        val contactsState = ContactsState(
            isLoading = false,
            contacts = listOf(),
            failedToGetContacts = true
        )

        val viewState = mapper.from(contactsState)

        val expectedViewState = ContactsViewState(
            isSpinnerVisible = false,
            contactItemRows = listOf(),
            contactsContent = ContactsContent.ERROR_GETTING_CONTACTS
        )
        assertEquals(expectedViewState, viewState)
    }

    @Test
    fun `WHEN from is called with empty contacts state, THEN it returns a view state with empty content`() {
        val contactsState = ContactsState(
            isLoading = false,
            contacts = listOf(),
            failedToGetContacts = false
        )

        val viewState = mapper.from(contactsState)

        val expectedViewState = ContactsViewState(
            isSpinnerVisible = false,
            contactItemRows = listOf(),
            contactsContent = ContactsContent.EMPTY_CONTACTS
        )
        assertEquals(expectedViewState, viewState)
    }

    @Test
    fun `WHEN from is called with multiple contacts, THEN it chunks contacts into rows of size 2`() {
        val contact1 = Contact.EmailContact(id = "1", email = "contact1@example.com")
        val contact2 = Contact.EmailContact(id = "2", email = "contact2@example.com")
        val contact3 = Contact.EmailContact(id = "3", email = "contact3@example.com")
        val contact4 = Contact.EmailContact(id = "4", email = "contact4@example.com")

        val contactItem1 = ContactItem(id = "1", displayName = "contact1@example.com")
        val contactItem2 = ContactItem(id = "2", displayName = "contact2@example.com")
        val contactItem3 = ContactItem(id = "3", displayName = "contact3@example.com")
        val contactItem4 = ContactItem(id = "4", displayName = "contact4@example.com")

        every { mockContactItemMapper.from(contact1) } returns contactItem1
        every { mockContactItemMapper.from(contact2) } returns contactItem2
        every { mockContactItemMapper.from(contact3) } returns contactItem3
        every { mockContactItemMapper.from(contact4) } returns contactItem4

        val contactsState = ContactsState(
            isLoading = false,
            contacts = listOf(contact1, contact2, contact3, contact4),
            failedToGetContacts = false
        )

        val viewState = mapper.from(contactsState)

        val expectedContactItemRows = listOf(
            listOf(contactItem1, contactItem2),
            listOf(contactItem3, contactItem4)
        )

        val expectedViewState = ContactsViewState(
            isSpinnerVisible = false,
            contactItemRows = expectedContactItemRows,
            contactsContent = ContactsContent.CONTACTS
        )

        assertEquals(expectedViewState, viewState)
    }

    @Test
    fun `WHEN from is called with an odd number of contacts, THEN it chunks contacts correctly with the last row smaller`() {
        val contact1 = Contact.EmailContact(id = "1", email = "contact1@example.com")
        val contact2 = Contact.EmailContact(id = "2", email = "contact2@example.com")
        val contact3 = Contact.EmailContact(id = "3", email = "contact3@example.com")
        val contactItem1 = ContactItem(id = "1", displayName = "contact1@example.com")
        val contactItem2 = ContactItem(id = "2", displayName = "contact2@example.com")
        val contactItem3 = ContactItem(id = "3", displayName = "contact3@example.com")

        every { mockContactItemMapper.from(contact1) } returns contactItem1
        every { mockContactItemMapper.from(contact2) } returns contactItem2
        every { mockContactItemMapper.from(contact3) } returns contactItem3

        val contactsState = ContactsState(
            isLoading = false,
            contacts = listOf(contact1, contact2, contact3),
            failedToGetContacts = false
        )

        val viewState = mapper.from(contactsState)

        val expectedContactItemRows = listOf(
            listOf(contactItem1, contactItem2),
            listOf(contactItem3)
        )

        val expectedViewState = ContactsViewState(
            isSpinnerVisible = false,
            contactItemRows = expectedContactItemRows,
            contactsContent = ContactsContent.CONTACTS
        )

        assertEquals(expectedViewState, viewState)
    }
}
