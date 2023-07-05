package com.mitteloupe.whoami.home.ui.model

data class ConnectionDetailsUiModel(
    val ipAddress: String,
    val city: String?,
    val region: String?,
    val countryName: String?,
    val geolocation: String?,
    val postCode: String?,
    val timeZone: String?,
    val internetServiceProviderName: String?
)
