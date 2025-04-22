package com.activecampaign.contacts.data.mapper

import com.activecampaign.contacts.domain.model.ContactField
import com.activecampaign.contacts.domain.model.Order
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DataOrderingMapperTest {

    private val mapper = DataOrderingMapper()

    @Test
    fun `WHEN map contains FIRST_NAME ASCENDING THEN returns correct API map`() {
        val input = mapOf(ContactField.FIRST_NAME to Order.ASCENDING)

        val result = mapper.from(input)

        val expected = mapOf("order[firstName]" to "ASC")
        assertEquals(expected, result)
    }

    @Test
    fun `WHEN map contains LAST_NAME DESCENDING THEN returns correct API map`() {
        val input = mapOf(ContactField.LAST_NAME to Order.DESCENDING)

        val result = mapper.from(input)

        val expected = mapOf("order[lastName]" to "DESC")
        assertEquals(expected, result)
    }

    @Test
    fun `WHEN map contains multiple fields THEN returns all mapped`() {
        val input = mapOf(
            ContactField.FIRST_NAME to Order.ASCENDING,
            ContactField.EMAIL to Order.DESCENDING
        )

        val result = mapper.from(input)

        val expected = mapOf(
            "order[firstName]" to "ASC",
            "order[email]" to "DESC"
        )
        assertEquals(expected, result)
    }

    @Test
    fun `WHEN map is empty THEN returns empty map`() {
        val result = mapper.from(emptyMap())

        assertEquals(emptyMap(), result)
    }
}
