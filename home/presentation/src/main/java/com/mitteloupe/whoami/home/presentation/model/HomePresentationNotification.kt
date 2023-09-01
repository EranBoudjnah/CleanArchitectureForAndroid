package com.mitteloupe.whoami.home.presentation.model

import com.mitteloupe.whoami.architecture.presentation.notification.PresentationNotification

sealed interface HomePresentationNotification : PresentationNotification {
    data class ConnectionSaved(val ipAddress: String) : HomePresentationNotification
}
