package com.mitteloupe.whoami.home.ui.view.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.analytics.AnalyticsEvent
import com.mitteloupe.whoami.home.ui.R

@Composable
fun HomeFooter(
    connected: Boolean,
    analytics: Analytics,
    onSaveDetailsClick: () -> Unit,
    onViewHistoryClick: () -> Unit,
    onOpenSourceNoticesClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 16.dp)
    ) {
        NavigationButtons(
            connected = connected,
            analytics = analytics,
            onSaveDetailsClick = { onSaveDetailsClick() },
            onViewHistoryClick = { onViewHistoryClick() }
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clickable { onOpenSourceNoticesClick() }
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.home_open_source_notices_label),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    HomeFooter(
        connected = true,
        analytics = object : Analytics {
            override fun logScreen(screenName: String) = Unit

            override fun logEvent(event: AnalyticsEvent) = Unit
        },
        onSaveDetailsClick = {},
        onViewHistoryClick = {},
        onOpenSourceNoticesClick = {}
    )
}
