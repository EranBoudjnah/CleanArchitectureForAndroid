package com.mitteloupe.whoami.home.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import com.mitteloupe.whoami.home.presentation.model.HomeNotification

@Composable
fun NotificationToast(
    notification: State<HomeNotification?>,
    notificationValue: HomeNotification.ConnectionSaved
) {
    val context = LocalContext.current
    LaunchedEffect(notification) {
        Toast.makeText(
            context,
            context.getString(
                R.string.home_details_saved_notification,
                notificationValue.ipAddress
            ),
            Toast.LENGTH_SHORT
        ).show()
    }
}
