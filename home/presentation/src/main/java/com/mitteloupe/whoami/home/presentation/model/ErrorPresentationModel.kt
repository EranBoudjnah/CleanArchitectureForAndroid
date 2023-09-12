package com.mitteloupe.whoami.home.presentation.model

sealed interface ErrorPresentationModel {
    data object RequestTimeout : ErrorPresentationModel

    data object NoIpAddress : ErrorPresentationModel

    data class NoIpAddressInformation(val ipAddress: String) : ErrorPresentationModel

    data object Unknown : ErrorPresentationModel
}
