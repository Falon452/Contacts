package com.activecampaign.contacs.presentation.mapper

import com.activecampaign.contacs.presentation.model.ContactItem
import com.activecampaign.contacts.domain.model.Contact
import com.activecampaign.contacts.domain.model.Contact.EmailContact
import com.activecampaign.contacts.domain.model.Contact.FullNameContact
import javax.inject.Inject

class ContactItemMapper @Inject constructor() {

    fun from(contact: Contact): ContactItem =
        when (contact) {
            is EmailContact -> ContactItem(
                id = contact.id,
                displayName = contact.email
            )
            is FullNameContact -> ContactItem(
                id = contact.id,
                displayName = "${contact.firstName} ${contact.lastName}"
            )
        }
}
