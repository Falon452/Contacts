package com.activecampaign.contacts.data.models

import com.google.gson.annotations.SerializedName

internal data class GetContactsResponse(
    @SerializedName("scoreValues") val scoreValues: List<Any>?,
    @SerializedName("contacts") val contacts: List<ContactResponse>?,
    @SerializedName("meta") val meta: Meta?
)

internal data class ContactResponse(
    @SerializedName("cdate") val cdate: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("orgid") val orgId: String?,
    @SerializedName("orgname") val orgName: String?,
    @SerializedName("segmentio_id") val segmentioId: String?,
    @SerializedName("bounced_hard") val bouncedHard: String?,
    @SerializedName("bounced_soft") val bouncedSoft: String?,
    @SerializedName("bounced_date") val bouncedDate: String?,
    @SerializedName("ip") val ip: String?,
    @SerializedName("ua") val userAgent: String?,
    @SerializedName("hash") val hash: String?,
    @SerializedName("socialdata_lastcheck") val socialDataLastCheck: String?,
    @SerializedName("email_local") val emailLocal: String?,
    @SerializedName("email_domain") val emailDomain: String?,
    @SerializedName("sentcnt") val sentCount: String?,
    @SerializedName("rating_tstamp") val ratingTimestamp: String?,
    @SerializedName("gravatar") val gravatar: String?,
    @SerializedName("deleted") val deleted: String?,
    @SerializedName("anonymized") val anonymized: String?,
    @SerializedName("adate") val adate: String?,
    @SerializedName("udate") val udate: String?,
    @SerializedName("edate") val edate: String?,
    @SerializedName("deleted_at") val deletedAt: String?,
    @SerializedName("created_utc_timestamp") val createdUtcTimestamp: String?,
    @SerializedName("updated_utc_timestamp") val updatedUtcTimestamp: String?,
    @SerializedName("created_timestamp") val createdTimestamp: String?,
    @SerializedName("updated_timestamp") val updatedTimestamp: String?,
    @SerializedName("created_by") val createdBy: String?,
    @SerializedName("updated_by") val updatedBy: String?,
    @SerializedName("mpp_tracking") val mppTracking: String?,
    @SerializedName("last_click_date") val lastClickDate: String?,
    @SerializedName("last_open_date") val lastOpenDate: String?,
    @SerializedName("last_mpp_open_date") val lastMppOpenDate: String?,
    @SerializedName("best_send_hour") val bestSendHour: String?,
    @SerializedName("scoreValues") val scoreValues: List<Any>?,
    @SerializedName("accountContacts") val accountContacts: List<Any>?,
    @SerializedName("links") val links: ContactLinks?,
    @SerializedName("id") val id: String?,
    @SerializedName("organization") val organization: Any?
)

internal data class ContactLinks(
    @SerializedName("bounceLogs") val bounceLogs: String?,
    @SerializedName("contactAutomations") val contactAutomations: String?,
    @SerializedName("contactData") val contactData: String?,
    @SerializedName("contactGoals") val contactGoals: String?,
    @SerializedName("contactLists") val contactLists: String?,
    @SerializedName("contactLogs") val contactLogs: String?,
    @SerializedName("contactTags") val contactTags: String?,
    @SerializedName("contactDeals") val contactDeals: String?,
    @SerializedName("deals") val deals: String?,
    @SerializedName("fieldValues") val fieldValues: String?,
    @SerializedName("geoIps") val geoIps: String?,
    @SerializedName("notes") val notes: String?,
    @SerializedName("organization") val organization: String?,
    @SerializedName("plusAppend") val plusAppend: String?,
    @SerializedName("trackingLogs") val trackingLogs: String?,
    @SerializedName("scoreValues") val scoreValues: String?,
    @SerializedName("accountContacts") val accountContacts: String?,
    @SerializedName("automationEntryCounts") val automationEntryCounts: String?
)

internal data class Meta(
    @SerializedName("page_input") val pageInput: PageInput?
)

internal data class PageInput(
    @SerializedName("segmentid") val segmentId: Int?,
    @SerializedName("formid") val formId: Int?,
    @SerializedName("listid") val listId: Int?,
    @SerializedName("tagid") val tagId: Int?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("offset") val offset: Int?,
    @SerializedName("search") val search: String?,
    @SerializedName("sort") val sort: String?,
    @SerializedName("seriesid") val seriesId: Int?,
    @SerializedName("waitid") val waitId: Int?,
    @SerializedName("status") val status: Int?,
    @SerializedName("forceQuery") val forceQuery: Int?,
    @SerializedName("cacheid") val cacheId: String?
)
