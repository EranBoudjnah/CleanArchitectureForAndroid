package com.mitteloupe.whoami.opensourcenotices.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer
import com.mitteloupe.whoami.architecture.ui.view.ScreenEnterObserver
import com.mitteloupe.whoami.opensourcenotices.ui.R
import com.mitteloupe.whoami.opensourcenotices.ui.di.OpenSourceNoticesDependencies

@Composable
fun OpenSourceNoticesDependencies.OpenSourceNoticesScreen() {
    ScreenEnterObserver {
        analytics.logScreen("Home")
    }

    OpenSourceNoticesScreenContent()
}

@Composable
private fun OpenSourceNoticesScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = stringResource(R.string.about_licenses_title),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(10.dp, 48.dp, 10.dp, 0.dp)
        )

        LibrariesContainer(
            libraries = null,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.height(100.dp)) {
        OpenSourceNoticesScreenContent()
    }
}
