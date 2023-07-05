package com.mitteloupe.whoami.home.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mitteloupe.whoami.home.ui.R

@Composable
fun DisconnectedContent(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(12.dp, 8.dp, 12.dp, 0.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_disconnected),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(MaterialTheme.colorScheme.primary)
                .scale(0.8f),
            contentScale = ContentScale.Fit
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.home_disconnected_description),
                fontSize = 14.sp,
                lineHeight = 16.sp,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        DisconnectedContent(modifier = Modifier.fillMaxWidth())
    }
}
