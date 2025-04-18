package com.activecampaign.contacs.presentation.model

sealed interface ContactsEvent {

    data class ShowToast(val message: String) : ContactsEvent
}
