package com.mitteloupe.whoami.datasource.history.model

data class NewIpAddressHistoryRecordDataModel(
    val ipAddress: String,
    val city: String?,
    val region: String?,
    val country: String?,
    val geolocation: String?,
    val internetServiceProviderName: String?,
    val postCode: String?,
    val timeZone: String?
)
