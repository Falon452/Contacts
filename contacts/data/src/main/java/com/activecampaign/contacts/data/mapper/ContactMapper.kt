package com.activecampaign.contacts.data.mapper

import com.activecampaign.contacts.data.models.ContactResponse
import com.activecampaign.contacts.domain.model.Contact
import javax.inject.Inject

internal class ContactMapper @Inject constructor() {

    fun from(contactResponse: ContactResponse): Contact? =
        with(contactResponse) {
            when {
                firstName?.isNotBlank() == true && lastName?.isNotBlank() == true -> {
                    Contact.FullNameContact(
                        firstName = firstName,
                        lastName = lastName,
                    )
                }
                email?.isNotBlank() == true -> {
                    Contact.EmailContact(
                        email = email,
                    )
                }
                else -> null
            }
        }
}
