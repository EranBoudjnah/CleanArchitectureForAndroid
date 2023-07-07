package com.mitteloupe.whoami.home.ui.model

data class ConnectionDetailsUiModel(
    val ipAddress: String,
    val city: IconLabelUiModel?,
    val region: IconLabelUiModel?,
    val countryName: IconLabelUiModel?,
    val geolocation: IconLabelUiModel?,
    val postCode: IconLabelUiModel?,
    val timeZone: IconLabelUiModel?,
    val internetServiceProviderName: IconLabelUiModel?
)
