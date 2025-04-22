package com.activecampaign.contacts.data.datasource

import com.activecampaign.contacts.data.models.GetContactsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

internal interface ContactsApi {

    @GET("contacts")
    suspend fun getContacts(
        @Query("limit") limit: Int,
        @QueryMap(encoded = true) orders: Map<String, String>,
    ): GetContactsResponse
}
