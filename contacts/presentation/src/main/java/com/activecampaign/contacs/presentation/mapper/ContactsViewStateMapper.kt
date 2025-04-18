package com.activecampaign.contacs.presentation.mapper

import com.activecampaign.contacs.presentation.model.ContactsState
import com.activecampaign.contacs.presentation.model.ContactsViewState
import javax.inject.Inject

class ContactsViewStateMapper @Inject constructor() {

    fun from(state: ContactsState): ContactsViewState = with(state) {
        ContactsViewState(
            contacts = state.contacts ?: emptyList(),
            showSpinner = state.contacts == null,
            showListEmptyImage = state.contacts?.isEmpty() == true
        )
    }
}
