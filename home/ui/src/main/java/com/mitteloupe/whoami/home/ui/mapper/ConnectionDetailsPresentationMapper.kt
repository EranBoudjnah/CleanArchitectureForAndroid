package com.mitteloupe.whoami.home.ui.mapper

import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.ui.model.ConnectionDetailsUiModel
import java.util.*

private val locales by lazy {
    Locale.getISOCountries().map { country -> Locale("en", country) }
}

class ConnectionDetailsPresentationMapper {
    fun toPresentation(connectionDetails: ConnectionDetailsUiModel) = HomeViewState.Connected(
        ipAddress = connectionDetails.ipAddress,
        city = connectionDetails.cityIconLabel?.label,
        region = connectionDetails.regionIconLabel?.label,
        geolocation = connectionDetails.geolocationIconLabel?.label?.replace(", ", ","),
        postCode = connectionDetails.postCode?.label,
        timeZone = connectionDetails.timeZone?.label,
        internetServiceProviderName = connectionDetails.internetServiceProviderName?.label,
        countryCode = connectionDetails.countryIconLabel?.label?.let { countryName ->
            locales.firstOrNull { locale ->
                locale.displayCountry == countryName
            }
        }?.country
    )
}
