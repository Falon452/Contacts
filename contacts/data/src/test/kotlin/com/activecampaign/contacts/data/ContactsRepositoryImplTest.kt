package com.activecampaign.contacts.data

import com.activecampaign.contacts.data.datasource.ContactsApi
import com.activecampaign.contacts.data.mapper.ContactMapper
import com.activecampaign.contacts.data.mapper.DataOrderingMapper
import com.activecampaign.contacts.data.models.ContactResponse
import com.activecampaign.contacts.data.models.GetContactsResponse
import com.activecampaign.contacts.domain.model.Contact
import com.activecampaign.contacts.domain.model.ContactOrdering
import com.activecampaign.contacts.domain.model.Order
import com.activecampaign.contacts.domain.repository.ContactsRepository
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ContactsRepositoryImplTest {

    private val mockContactsApi = mockk<ContactsApi>()
    private val mockContactMapper = mockk<ContactMapper>()
    private val mockDataOrderingMapper = mockk<DataOrderingMapper>()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: ContactsRepository

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = ContactsRepositoryImpl(
            contactMapper = mockContactMapper,
            contactsApi = mockContactsApi,
            dataOrderingMapper = mockDataOrderingMapper,
            ioDispatcher = testDispatcher
        )
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN getContacts is called with valid parameters and ASC ordering, THEN it returns a list of contacts in ascending order`() = runTest {
        val limit = 5
        val ordering = mapOf(ContactOrdering.NAME to Order.ASCENDING)
        val requestOrdering = mapOf("name" to "ASC")
        val mockResponse = mockk<GetContactsResponse>()
        val mockContactResponse = mockk<ContactResponse>()
        val mockContact = mockk<Contact>()

        every { mockDataOrderingMapper.from(ordering) } returns requestOrdering
        coEvery { mockContactsApi.getContacts(limit = limit, requestOrdering) } returns mockResponse
        every { mockResponse.contacts } returns listOf(mockContactResponse)
        every { mockContactMapper.from(mockContactResponse) } returns mockContact

        val result = repository.getContacts(limit, ordering)

        val expected = listOf(mockContact)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `WHEN getContacts is called with valid parameters and DESC ordering, THEN it returns a list of contacts in descending order`() = runTest {
        val limit = 5
        val ordering = mapOf(ContactOrdering.NAME to Order.DESCENDING)
        val requestOrdering = mapOf("name" to "DESC")
        val mockResponse = mockk<GetContactsResponse>()
        val mockContactResponse = mockk<ContactResponse>()
        val mockContact = mockk<Contact>()

        every { mockDataOrderingMapper.from(ordering) } returns requestOrdering
        coEvery { mockContactsApi.getContacts(limit = limit, requestOrdering) } returns mockResponse
        every { mockResponse.contacts } returns listOf(mockContactResponse)
        every { mockContactMapper.from(mockContactResponse) } returns mockContact

        val result = repository.getContacts(limit, ordering)

        val expected = listOf(mockContact)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `WHEN getContacts is called and API returns null contacts, THEN it returns an empty list`() = runTest {
        val limit = 5
        val ordering = mapOf(ContactOrdering.NAME to Order.ASCENDING)
        val mockResponse = mockk<GetContactsResponse>(relaxed = true)
        val requestOrdering = mapOf("name" to "ASC")

        every { mockDataOrderingMapper.from(ordering) } returns requestOrdering
        coEvery { mockContactsApi.getContacts(limit = limit, any()) } returns mockResponse
        every { mockResponse.contacts } returns null

        val result = repository.getContacts(limit, ordering)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull().isNullOrEmpty())
    }

    @Test
    fun `WHEN getContacts is called and API throws an exception, THEN it returns a failure`() = runTest {
        val limit = 5
        val ordering = mapOf(ContactOrdering.NAME to Order.ASCENDING)
        val requestOrdering = mapOf("name" to "ASC")
        val exception = Exception("API error")

        every { mockDataOrderingMapper.from(ordering) } returns requestOrdering
        coEvery { mockContactsApi.getContacts(limit = limit, any()) } throws exception

        val result = repository.getContacts(limit, ordering)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `WHEN getContacts is called and contacts contains null elements, THEN it maps only non-null contacts`() = runTest {
        val limit = 12
        val ordering = mapOf(ContactOrdering.NAME to Order.ASCENDING)
        val requestOrdering = mapOf("name" to "ASC")

        val mockResponse = mockk<GetContactsResponse>()
        val mockContactResponse1 = mockk<ContactResponse>()
        val mockContactResponse2 = mockk<ContactResponse>()
        val mockContact = mockk<Contact>()

        every { mockDataOrderingMapper.from(ordering) } returns requestOrdering
        coEvery { mockContactsApi.getContacts(limit = limit, requestOrdering) } returns mockResponse
        every { mockResponse.contacts } returns listOf(mockContactResponse1, mockContactResponse2)
        every { mockContactMapper.from(mockContactResponse1) } returns mockContact
        every { mockContactMapper.from(mockContactResponse2) } returns null

        val result = repository.getContacts(limit, ordering)

        val expected = listOf(mockContact)
        assertEquals(expected, result.getOrNull())
    }
}
