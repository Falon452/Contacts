package com.activecampaign.contacts.data.mapper

import android.util.Log
import com.activecampaign.contacts.data.models.ContactResponse
import com.activecampaign.contacts.domain.model.Contact
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ContactMapperTest {

    private val mapper = ContactMapper()

    @BeforeEach
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.w(any(), any<String>()) } returns 0
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `WHEN from is called with valid full name contact, THEN it returns FullNameContact`() {
        val response = ContactResponse(
            id = "123",
            firstName = "John",
            lastName = "Doe",
            email = "john@example.com"
        )

        val result = mapper.from(response)

        val expected = Contact.FullNameContact(
            id = "123",
            firstName = "John",
            lastName = "Doe"
        )
        assertEquals(expected, result)
    }

    @Test
    fun `WHEN from is called with valid email contact and missing first and last name, THEN it returns EmailContact`() {
        val response = ContactResponse(
            id = "456",
            firstName = null,
            lastName = null,
            email = "jane@example.com"
        )

        val result = mapper.from(response)

        val expected = Contact.EmailContact(
            id = "456",
            email = "jane@example.com"
        )
        assertEquals(expected, result)
    }

    @Test
    fun `WHEN from is called with missing id, THEN it returns null and logs a warning`() {
        val response = ContactResponse(
            id = null,
            firstName = "John",
            lastName = "Doe",
            email = "john@example.com"
        )

        val result = mapper.from(response)

        assertNull(result)
        verify(exactly = 1) { Log.w(any(), "Skipping contact: $response") }
    }

    @Test
    fun `WHEN from is called with blank first and last name and blank email, THEN it returns null and logs a warning`() {
        val response = ContactResponse(
            id = "789",
            firstName = " ",
            lastName = " ",
            email = " "
        )

        val result = mapper.from(response)

        assertNull(result)
        verify(exactly = 1)  { Log.w(any(), "Skipping contact: $response") }
    }

    @Test
    fun `WHEN from is called with blank id, THEN it returns null and logs a warning`() {
        val response = ContactResponse(
            id = " ",
            firstName = "John",
            lastName = "Doe",
            email = "john@example.com"
        )

        val result = mapper.from(response)

        assertNull(result)
        verify(exactly = 1)  { Log.w(any(), "Skipping contact: $response") }
    }
}
