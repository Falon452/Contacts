package com.activecampaign.contacts.data.mapper

import com.activecampaign.contacts.domain.model.ContactField
import com.activecampaign.contacts.domain.model.Order
import javax.inject.Inject

internal class DataOrderingMapper @Inject constructor() {

    fun from(ordering: Map<ContactField, Order>): Map<String, String> =
        ordering.map { (field, order) -> field.toData() to order.toData() }.toMap()

    private fun ContactField.toData(): String {
        val field = when (this) {
            ContactField.FIRST_NAME -> "firstName"
            ContactField.LAST_NAME -> "lastName"
            ContactField.EMAIL -> "email"
        }
        return "order[$field]"
    }

    private fun Order.toData() =
        when (this) {
            Order.ASCENDING -> "ASC"
            Order.DESCENDING -> "DESC"
        }
}
