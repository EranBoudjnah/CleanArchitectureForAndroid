package com.mitteloupe.whoami.home.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConnectionDetailsUiModel(
    val ipAddress: String,
    val cityIconLabel: IconLabelUiModel?,
    val regionIconLabel: IconLabelUiModel?,
    val countryIconLabel: IconLabelUiModel?,
    val geolocationIconLabel: IconLabelUiModel?,
    val postCode: IconLabelUiModel?,
    val timeZone: IconLabelUiModel?,
    val internetServiceProviderName: IconLabelUiModel?
) : Parcelable
