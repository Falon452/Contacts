package com.activecampaign.contacs.presentation.mapper

import com.activecampaign.contacs.presentation.model.ContactsState
import com.activecampaign.contacs.presentation.model.ContactsViewState
import javax.inject.Inject

class ContactsViewStateMapper @Inject constructor(
    private val contactItemMapper: ContactItemMapper,
) {

    fun from(state: ContactsState): ContactsViewState = with(state) {
        ContactsViewState(
            contactItemRows = state.contacts?.map(contactItemMapper::from)?.chunked(COLUMNS_IN_ROW) ?: emptyList(),
            showSpinner = state.contacts == null,
            showListEmptyImage = state.contacts?.isEmpty() == true
        )
    }

    private companion object {

        const val COLUMNS_IN_ROW = 2
    }
}
