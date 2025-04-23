package com.activecampaign.contacts.domain.repository

import com.activecampaign.contacts.domain.model.Contact
import com.activecampaign.contacts.domain.model.ContactOrdering
import com.activecampaign.contacts.domain.model.Order

interface ContactsRepository {

    suspend fun getContacts(limit: Int, ordering: Map<ContactOrdering, Order>): Result<List<Contact>>
}
