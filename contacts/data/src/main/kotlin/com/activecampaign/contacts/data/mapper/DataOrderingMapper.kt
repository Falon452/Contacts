package com.activecampaign.contacts.data.mapper

import com.activecampaign.contacts.domain.model.ContactOrdering
import com.activecampaign.contacts.domain.model.Order
import javax.inject.Inject

internal class DataOrderingMapper @Inject constructor() {

    fun from(ordering: Map<ContactOrdering, Order>): Map<String, String> =
        ordering.map { (field, order) -> field.toData() to order.toData() }.toMap()

    private fun ContactOrdering.toData(): String {
        val field = when (this) {
            ContactOrdering.NAME -> "name"
            ContactOrdering.EMAIL -> "email"
        }
        return "orders[$field]"
    }

    private fun Order.toData() =
        when (this) {
            Order.ASCENDING -> "ASC"
            Order.DESCENDING -> "DESC"
        }
}
