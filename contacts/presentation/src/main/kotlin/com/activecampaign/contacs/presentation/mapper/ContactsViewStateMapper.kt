package com.activecampaign.contacs.presentation.mapper

import com.activecampaign.contacs.presentation.model.ContactsContentState
import com.activecampaign.contacs.presentation.model.ContactsState
import com.activecampaign.contacs.presentation.model.ContactsViewState
import javax.inject.Inject

class ContactsViewStateMapper @Inject constructor(
    private val contactItemMapper: ContactItemMapper,
) {

    fun from(state: ContactsState): ContactsViewState = with(state) {
        ContactsViewState(
            isSpinnerVisible = isLoading,
            contactItemRows = state.contacts.map(contactItemMapper::from).chunked(COLUMNS_IN_ROW),
            contactsContentState = contactsContentFrom(state),
        )
    }

    private fun contactsContentFrom(state: ContactsState): ContactsContentState =
        when {
            state.failedToGetContacts -> ContactsContentState.ERROR_GETTING_CONTACTS
            state.contacts.isEmpty() && !state.isLoading -> ContactsContentState.EMPTY_CONTACTS
            else -> ContactsContentState.CONTACTS
        }

    private companion object {

        const val COLUMNS_IN_ROW = 2
    }
}
