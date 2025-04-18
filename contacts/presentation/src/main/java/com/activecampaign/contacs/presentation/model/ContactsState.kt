package com.activecampaign.contacs.presentation.model

import com.activecampaign.contacts.domain.model.Contact

data class ContactsState(
    val contacts: List<Contact>?,
)
