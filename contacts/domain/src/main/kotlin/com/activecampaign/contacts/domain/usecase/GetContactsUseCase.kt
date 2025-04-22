package com.activecampaign.contacts.domain.usecase

import com.activecampaign.contacts.domain.model.Contact
import com.activecampaign.contacts.domain.model.ContactField.EMAIL
import com.activecampaign.contacts.domain.model.ContactField.FIRST_NAME
import com.activecampaign.contacts.domain.model.ContactField.LAST_NAME
import com.activecampaign.contacts.domain.model.Order.DESCENDING
import com.activecampaign.contacts.domain.repository.ContactsRepository
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository,
) {

    suspend fun execute(): Result<List<Contact>> =
        contactsRepository.getContacts(
            ordering = mapOf(
                FIRST_NAME to DESCENDING,
                LAST_NAME to DESCENDING,
                EMAIL to DESCENDING,
            ),
            limit = LIMIT
        )

    private companion object {

        const val LIMIT = 50
    }
}
