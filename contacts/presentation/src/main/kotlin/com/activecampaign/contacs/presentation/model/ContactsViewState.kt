package com.activecampaign.contacs.presentation.model

data class ContactsViewState(
    val contactItemRows: List<List<ContactItem>>,
    val contactsContentState: ContactsContentState,
    val isSpinnerVisible: Boolean,
)

enum class ContactsContentState {
    CONTACTS,
    EMPTY_CONTACTS,
    ERROR_GETTING_CONTACTS,
}
