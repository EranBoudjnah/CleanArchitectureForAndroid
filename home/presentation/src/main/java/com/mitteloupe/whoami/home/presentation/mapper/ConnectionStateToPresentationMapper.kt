package com.mitteloupe.whoami.home.presentation.mapper

import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel.Connected
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel.Disconnected
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel.Error
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel.Unset
import com.mitteloupe.whoami.home.presentation.model.HomeViewState

class ConnectionStateToPresentationMapper(
    private val exceptionToPresentationMapper: ExceptionToPresentationMapper
) {
    fun toPresentation(connectionDetails: ConnectionDetailsDomainModel) =
        when (connectionDetails) {
            is Connected -> HomeViewState.Connected(
                ipAddress = connectionDetails.ipAddress,
                city = connectionDetails.city,
                region = connectionDetails.region,
                countryCode = connectionDetails.country,
                geolocation = connectionDetails.geolocation,
                internetServiceProviderName = connectionDetails.internetServiceProviderName,
                postCode = connectionDetails.postCode,
                timeZone = connectionDetails.timeZone
            )

            Disconnected -> HomeViewState.Disconnected
            Unset -> HomeViewState.Loading
            is Error -> {
                HomeViewState.Error(
                    exceptionToPresentationMapper.toPresentation(connectionDetails.exception)
                )
            }
        }
}
