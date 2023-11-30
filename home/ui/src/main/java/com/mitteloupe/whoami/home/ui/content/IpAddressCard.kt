package com.mitteloupe.whoami.home.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mitteloupe.whoami.home.ui.R

@Composable
fun IpAddressCard(ipAddress: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(16.dp, 8.dp, 16.dp, 0.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.main_card_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(0.dp),
            contentScale = ContentScale.FillHeight
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = ipAddress,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
            )
            Text(
                text = stringResource(R.string.home_ip_description),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    IpAddressCard(ipAddress = "128.0.0.1")
}
