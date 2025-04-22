package com.activecampaign.contacts.data.mapper

import android.util.Log
import com.activecampaign.contacts.data.models.ContactResponse
import com.activecampaign.contacts.domain.model.Contact
import javax.inject.Inject

internal class ContactMapper @Inject constructor() {

    fun from(contactResponse: ContactResponse): Contact? =
        with(contactResponse) {
            when {
                firstName?.isNotBlank() == true && lastName?.isNotBlank() == true && id?.isNotBlank() == true -> {
                    Contact.FullNameContact(
                        id = id,
                        firstName = firstName,
                        lastName = lastName,
                    )
                }
                email?.isNotBlank() == true && id?.isNotBlank() == true -> {
                    Contact.EmailContact(
                        id = id,
                        email = email,
                    )
                }
                else -> {
                    Log.w(TAG, "Skipping contact: $contactResponse")
                    null
                }
            }
        }

    private companion object {

        const val TAG = "ContactMapper"
    }
}
