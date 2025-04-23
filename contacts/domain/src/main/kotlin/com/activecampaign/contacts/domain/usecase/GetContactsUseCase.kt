package com.activecampaign.contacts.domain.usecase

import com.activecampaign.contacts.domain.model.Contact
import com.activecampaign.contacts.domain.model.ContactOrdering.NAME
import com.activecampaign.contacts.domain.model.Order.DESCENDING
import com.activecampaign.contacts.domain.repository.ContactsRepository
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository,
) {

    suspend fun execute(): Result<List<Contact>> =
        contactsRepository.getContacts(
            ordering = mapOf(
                NAME to DESCENDING,
            ),
            limit = LIMIT
        ).map {
            val (fullNames, emails) = it.partition { it is Contact.FullNameContact }
            fullNames + emails
        }

    private companion object {

        const val LIMIT = 50
    }
}
