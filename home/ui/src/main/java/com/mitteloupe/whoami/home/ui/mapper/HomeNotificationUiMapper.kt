package com.mitteloupe.whoami.home.ui.mapper

import android.content.Context
import com.mitteloupe.whoami.architecture.ui.notification.mapper.NotificationUiMapper
import com.mitteloupe.whoami.architecture.ui.notification.model.UiNotification
import com.mitteloupe.whoami.home.presentation.model.HomePresentationNotification
import com.mitteloupe.whoami.home.presentation.model.HomePresentationNotification.ConnectionSaved
import com.mitteloupe.whoami.home.ui.R
import com.mitteloupe.whoami.home.ui.view.widget.notificationToast as staticNotificationToast

class HomeNotificationUiMapper(
    private val context: Context,
    private val notificationToast: (Context, String) -> Unit = ::staticNotificationToast
) : NotificationUiMapper<HomePresentationNotification> {
    override fun toUi(notification: HomePresentationNotification): UiNotification =
        when (notification) {
            is ConnectionSaved -> {
                ConnectionSavedUiNotification(context, notification.ipAddress, notificationToast)
            }
        }

    private class ConnectionSavedUiNotification(
        private val context: Context,
        private val ipAddress: String,
        private val notificationToast: (Context, String) -> Unit
    ) : UiNotification {
        override fun present() {
            val text = context.getString(R.string.home_details_saved_notification, ipAddress)

            notificationToast(context, text)
        }
    }
}
