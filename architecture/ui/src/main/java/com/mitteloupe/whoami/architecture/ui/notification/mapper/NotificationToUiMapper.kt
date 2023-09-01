package com.mitteloupe.whoami.architecture.ui.notification.mapper

import com.mitteloupe.whoami.architecture.presentation.notification.PresentationNotification
import com.mitteloupe.whoami.architecture.ui.notification.model.UiNotification

interface NotificationToUiMapper {
    fun toUi(notification: PresentationNotification): UiNotification
}
