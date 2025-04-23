package com.activecampaign.contacts.data

import com.activecampaign.contacts.data.datasource.ContactsApi
import com.activecampaign.contacts.data.di.IoDispatcher
import com.activecampaign.contacts.data.mapper.ContactMapper
import com.activecampaign.contacts.data.mapper.DataOrderingMapper
import com.activecampaign.contacts.domain.model.Contact
import com.activecampaign.contacts.domain.model.ContactOrdering
import com.activecampaign.contacts.domain.model.Order
import com.activecampaign.contacts.domain.repository.ContactsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ContactsRepositoryImpl @Inject constructor(
    private val contactMapper: ContactMapper,
    private val contactsApi: ContactsApi,
    private val dataOrderingMapper: DataOrderingMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ContactsRepository {

    override suspend fun getContacts(
        limit: Int,
        ordering: Map<ContactOrdering, Order>
    ): Result<List<Contact>> =
        withContext(ioDispatcher) {
            runCatching {
                contactsApi.getContacts(limit = limit, dataOrderingMapper.from(ordering))
                    .contacts
                    ?.mapNotNull(contactMapper::from)
                    ?: emptyList()
            }
        }
}
