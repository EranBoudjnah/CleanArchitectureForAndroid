package com.mitteloupe.whoami.home.ui.view.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.analytics.AnalyticsEvent
import com.mitteloupe.whoami.analytics.event.Click
import com.mitteloupe.whoami.home.ui.R
import com.mitteloupe.whoami.home.ui.content.NavigationButton

@Composable
fun NavigationButtons(
    connected: Boolean,
    analytics: Analytics,
    onSaveDetailsClick: () -> Unit,
    onViewHistoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(top = 24.dp, bottom = 24.dp)
            .fillMaxWidth()
    ) {
        NavigationButton(
            iconResourceId = R.drawable.icon_save,
            label = stringResource(R.string.home_save_details_button_label),
            onClick = {
                if (connected) {
                    analytics.logEvent(
                        Click("Save Details", mapOf("State" to "Connected"))
                    )
                    onSaveDetailsClick()
                } else {
                    analytics.logEvent(
                        Click("Save Details", mapOf("State" to "Not connected"))
                    )
                }
            },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        NavigationButton(
            iconResourceId = R.drawable.icon_history,
            label = stringResource(R.string.home_history_button_label),
            onClick = {
                analytics.logEvent(Click("View History"))
                onViewHistoryClick()
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NavigationButtons(
        connected = true,
        analytics = object : Analytics {
            override fun logScreen(screenName: String) = Unit

            override fun logEvent(event: AnalyticsEvent) = Unit
        },
        onSaveDetailsClick = {},
        onViewHistoryClick = {}
    )
}
