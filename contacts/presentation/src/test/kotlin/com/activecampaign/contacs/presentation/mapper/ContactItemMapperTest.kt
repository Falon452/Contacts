package com.activecampaign.contacs.presentation.mapper

import com.activecampaign.contacs.presentation.model.ContactItem
import com.activecampaign.contacts.domain.model.Contact.EmailContact
import com.activecampaign.contacts.domain.model.Contact.FullNameContact
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ContactItemMapperTest {

    private val mapper = ContactItemMapper()

    @Test
    fun `WHEN from is called with EmailContact, THEN it maps correctly to ContactItem`() {
        val emailContact = EmailContact(id = "1", email = "test@example.com")

        val contactItem = mapper.from(emailContact)

        val expectedContactItem = ContactItem(id = "1", displayName = "test@example.com")
        assertEquals(expectedContactItem, contactItem)
    }

    @Test
    fun `WHEN from is called with FullNameContact, THEN it maps correctly to ContactItem`() {
        val fullNameContact = FullNameContact(id = "2", firstName = "John", lastName = "Doe")

        val contactItem = mapper.from(fullNameContact)

        val expectedContactItem = ContactItem(id = "2", displayName = "John Doe")
        assertEquals(expectedContactItem, contactItem)
    }
}
