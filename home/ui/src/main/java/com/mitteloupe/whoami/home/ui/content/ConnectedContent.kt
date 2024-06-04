package com.mitteloupe.whoami.home.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mitteloupe.whoami.home.ui.R
import com.mitteloupe.whoami.home.ui.model.ConnectionDetailsUiModel
import com.mitteloupe.whoami.home.ui.model.IconLabelUiModel

@Composable
fun ConnectedContent(connectionDetails: ConnectionDetailsUiModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        IpAddressCard(connectionDetails.ipAddress)

        DetailsBlock(connectionDetails)
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(colorScheme.surface)
    ) {
        ConnectedContent(
            connectionDetails = ConnectionDetailsUiModel(
                ipAddress = "0.0.0.0",
                cityIconLabel = IconLabelUiModel(R.drawable.icon_city, "Brentwood"),
                regionIconLabel = IconLabelUiModel(R.drawable.icon_region, "England"),
                countryIconLabel = IconLabelUiModel(R.drawable.icon_country, "GB"),
                geolocationIconLabel = IconLabelUiModel(R.drawable.icon_geolocation, "0, 0"),
                postCode = IconLabelUiModel(R.drawable.icon_post_code, "AB12 3CD"),
                timeZone = IconLabelUiModel(R.drawable.icon_time_zone, "Europe/London"),
                internetServiceProviderName =
                IconLabelUiModel(R.drawable.icon_internet_service_provider, "TalkTalk")
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
