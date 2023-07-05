package com.mitteloupe.whoami.datasource.ipaddressinformation.model

data class IpAddressInformationDataModel(
    val city: String?,
    val region: String?,
    val country: String?,
    val geolocation: String?,
    val internetServiceProviderName: String?,
    val postCode: String?,
    val timeZone: String?
)
