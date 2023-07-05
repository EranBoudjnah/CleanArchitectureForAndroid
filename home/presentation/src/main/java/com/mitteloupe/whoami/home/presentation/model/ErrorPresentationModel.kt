package com.mitteloupe.whoami.home.presentation.model

sealed interface ErrorPresentationModel {
    object RequestTimeout : ErrorPresentationModel

    object NoIpAddress : ErrorPresentationModel

    data class NoIpAddressInformation(val ipAddress: String) : ErrorPresentationModel

    object Unknown : ErrorPresentationModel
}
