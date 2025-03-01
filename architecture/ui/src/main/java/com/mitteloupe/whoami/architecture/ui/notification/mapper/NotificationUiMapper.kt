package com.mitteloupe.whoami.architecture.ui.notification.mapper

import com.mitteloupe.whoami.architecture.presentation.notification.PresentationNotification
import com.mitteloupe.whoami.architecture.ui.notification.model.UiNotification

interface NotificationUiMapper {
    fun toUi(notification: PresentationNotification): UiNotification
}
