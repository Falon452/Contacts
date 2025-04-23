package com.activecampaign.contacts.data.mapper

import com.activecampaign.contacts.domain.model.ContactOrdering
import com.activecampaign.contacts.domain.model.Order
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DataOrderingMapperTest {

    private val mapper = DataOrderingMapper()

    @Test
    fun `WHEN map contains NAME ASCENDING THEN returns correct API map`() {
        val input = mapOf(ContactOrdering.NAME to Order.ASCENDING)

        val result = mapper.from(input)

        val expected = mapOf("order[name]" to "ASC")
        assertEquals(expected, result)
    }

    @Test
    fun `WHEN map contains multiple fields THEN returns all mapped`() {
        val input = mapOf(
            ContactOrdering.NAME to Order.ASCENDING,
            ContactOrdering.EMAIL to Order.DESCENDING
        )

        val result = mapper.from(input)

        val expected = mapOf(
            "order[name]" to "ASC",
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
