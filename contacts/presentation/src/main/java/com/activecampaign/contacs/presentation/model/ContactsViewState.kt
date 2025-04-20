package com.activecampaign.contacs.presentation.model

data class ContactsViewState(
    val contactItemRows: List<List<ContactItem>>,
    val contactsContent: ContactsContent,
    val isSpinnerVisible: Boolean,
)

enum class ContactsContent {
    CONTACTS,
    EMPTY_CONTACTS,
    ERROR_GETTING_CONTACTS,
}
