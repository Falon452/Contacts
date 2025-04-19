package com.activecampaign.contacs.presentation.model

data class ContactsViewState(
    val contactItemRows: List<List<ContactItem>>,
    val showListEmptyImage: Boolean,
    val showSpinner: Boolean,
)
