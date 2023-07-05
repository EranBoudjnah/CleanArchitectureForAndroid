package com.mitteloupe.whoami.home.ui.mapper

import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.ui.model.ConnectionDetailsUiModel
import java.util.Locale

class ConnectionDetailsToUiMapper(
    private val toCountryName: String.() -> String = {
        val countryLocale = Locale("", this)
        countryLocale.displayCountry
    }
) {
    fun toUi(connectionDetails: HomeViewState.Connected) = ConnectionDetailsUiModel(
        ipAddress = connectionDetails.ipAddress,
        city = connectionDetails.city,
        region = connectionDetails.region,
        countryName = connectionDetails.countryCode?.toCountryName(),
        geolocation = connectionDetails.geolocation?.replace(",", ", "),
        postCode = connectionDetails.postCode,
        timeZone = connectionDetails.timeZone,
        internetServiceProviderName = connectionDetails.internetServiceProviderName
    )
}
