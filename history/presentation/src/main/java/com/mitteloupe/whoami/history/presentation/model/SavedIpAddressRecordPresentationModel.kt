package com.mitteloupe.whoami.history.presentation.model

data class SavedIpAddressRecordPresentationModel(
    val ipAddress: String,
    val city: String?,
    val region: String?,
    val countryCode: String?,
    val geolocation: String?,
    val internetServiceProviderName: String?,
    val postCode: String?,
    val timeZone: String?,
    val savedAtTimestampMilliseconds: Long
)
