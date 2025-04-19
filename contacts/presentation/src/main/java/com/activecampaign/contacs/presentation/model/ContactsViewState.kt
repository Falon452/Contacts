package com.activecampaign.contacs.presentation.model

data class ContactsViewState(
    val contacts: List<ContactItem>,
    val showListEmptyImage: Boolean,
    val showSpinner: Boolean,
)
