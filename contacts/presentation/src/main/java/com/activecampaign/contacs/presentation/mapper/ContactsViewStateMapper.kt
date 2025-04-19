package com.activecampaign.contacs.presentation.mapper

import com.activecampaign.contacs.presentation.model.ContactsState
import com.activecampaign.contacs.presentation.model.ContactsViewState
import javax.inject.Inject

class ContactsViewStateMapper @Inject constructor(
    private val contactItemMapper: ContactItemMapper,
) {

    fun from(state: ContactsState): ContactsViewState = with(state) {
        ContactsViewState(
            contacts = state.contacts?.map(contactItemMapper::from) ?: emptyList(),
            showSpinner = state.contacts == null,
            showListEmptyImage = state.contacts?.isEmpty() == true
        )
    }
}
