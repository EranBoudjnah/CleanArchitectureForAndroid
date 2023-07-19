package com.mitteloupe.whoami.home.presentation.model

sealed interface HomeNotification {
    data class ConnectionSaved(val ipAddress: String) : HomeNotification
}
