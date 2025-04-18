package com.activecampaign.contacts.domain.model


sealed class Contact {

    data class FullNameContact(
        val firstName: String,
        val lastName: String,
    ) : Contact()

    data class EmailContact(
        val email: String,
    ) : Contact()
}
