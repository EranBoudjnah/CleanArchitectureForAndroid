package com.mitteloupe.whoami.home.ui.mapper

import android.content.Context
import com.mitteloupe.whoami.architecture.presentation.notification.PresentationNotification
import com.mitteloupe.whoami.architecture.ui.notification.mapper.NotificationToUiMapper
import com.mitteloupe.whoami.architecture.ui.notification.model.UiNotification
import com.mitteloupe.whoami.home.presentation.model.HomePresentationNotification.ConnectionSaved
import com.mitteloupe.whoami.home.ui.R
import com.mitteloupe.whoami.home.ui.view.widget.notificationToast

class HomeNotificationToUiMapper(
    private val context: Context
) : NotificationToUiMapper {
    override fun toUi(notification: PresentationNotification): UiNotification =
        when (notification) {
            is ConnectionSaved -> ConnectionSavedUiNotification(context, notification.ipAddress)
            else -> error("Unhandled notification type: $notification")
        }

    private class ConnectionSavedUiNotification(
        private val context: Context,
        private val ipAddress: String
    ) : UiNotification {
        override fun present() {
            val text = context.getString(R.string.home_details_saved_notification, ipAddress)

            notificationToast(context = context, text = text)
        }
    }
}
