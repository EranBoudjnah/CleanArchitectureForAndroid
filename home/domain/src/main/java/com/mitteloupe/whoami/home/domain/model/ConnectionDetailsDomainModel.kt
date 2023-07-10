package com.mitteloupe.whoami.home.domain.model

import com.mitteloupe.whoami.architecture.domain.exception.DomainException

sealed interface ConnectionDetailsDomainModel {
    data class Connected(
        val ipAddress: String,
        val city: String?,
        val region: String?,
        val countryCode: String?,
        val geolocation: String?,
        val internetServiceProviderName: String?,
        val postCode: String?,
        val timeZone: String?
    ) : ConnectionDetailsDomainModel

    object Disconnected : ConnectionDetailsDomainModel

    data class Error(val exception: DomainException) : ConnectionDetailsDomainModel

    object Unset : ConnectionDetailsDomainModel
}
