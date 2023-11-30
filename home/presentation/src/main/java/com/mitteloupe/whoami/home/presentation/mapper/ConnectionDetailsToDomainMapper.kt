package com.mitteloupe.whoami.home.presentation.mapper

import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import com.mitteloupe.whoami.home.presentation.model.HomeViewState.Connected

class ConnectionDetailsToDomainMapper {
    fun toDomain(connectionDetails: Connected) = ConnectionDetailsDomainModel.Connected(
        ipAddress = connectionDetails.ipAddress,
        city = connectionDetails.city,
        region = connectionDetails.region,
        countryCode = connectionDetails.countryCode,
        geolocation = connectionDetails.geolocation,
        internetServiceProviderName = connectionDetails.internetServiceProviderName,
        postCode = connectionDetails.postCode,
        timeZone = connectionDetails.timeZone
    )
}
