package com.activecampaign.contacts.domain.model


sealed class Contact(
    open val id: String,
) {

    data class FullNameContact(
        override val id: String,
        val firstName: String,
        val lastName: String,
    ) : Contact(id)

    data class EmailContact(
        override val id: String,
        val email: String,
    ) : Contact(id)
}
