package com.activecampaign.contacts.data

import com.activecampaign.contacts.data.datasource.ContactsApi
import com.activecampaign.contacts.data.mapper.ContactMapper
import com.activecampaign.contacts.data.mapper.DataOrderingMapper
import com.activecampaign.contacts.domain.model.Contact
import com.activecampaign.contacts.domain.model.ContactField
import com.activecampaign.contacts.domain.model.Order
import com.activecampaign.contacts.domain.repository.ContactsRepository
import javax.inject.Inject

internal class ContactsRepositoryImpl @Inject constructor(
    private val contactMapper: ContactMapper,
    private val contactsApi: ContactsApi,
    private val dataOrderingMapper: DataOrderingMapper,
) : ContactsRepository {

    override suspend fun getContacts(
        limit: Int,
        ordering: Map<ContactField, Order>
    ): List<Contact> =
        contactsApi.getContacts(limit = limit, dataOrderingMapper.from(ordering))
            .contacts
            ?.map(contactMapper::from)
            ?: emptyList()
}
