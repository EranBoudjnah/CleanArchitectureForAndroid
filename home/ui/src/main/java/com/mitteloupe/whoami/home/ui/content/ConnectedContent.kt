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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mitteloupe.whoami.home.ui.R
import com.mitteloupe.whoami.home.ui.model.ConnectionDetailsUiModel
import com.mitteloupe.whoami.home.ui.model.IconLabelUiModel

@Composable
fun ConnectedContent(
    connectionDetails: ConnectionDetailsUiModel,
    modifier: Modifier = Modifier
) {
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
                text = connectionDetails.ipAddress,
                fontSize = 24.sp,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
            )
            Text(
                text = stringResource(R.string.home_ip_description),
                fontSize = 12.sp,
                lineHeight = 16.sp,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
            )
        }
    }

    Column(
        modifier = modifier.padding(16.dp, 24.dp, 16.dp, 0.dp)
    ) {
        Text(
            text = stringResource(R.string.home_details_title),
            fontSize = 24.sp,
            color = colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        listOf(
            R.string.home_city_label to
                connectionDetails.city,
            R.string.home_region_label to
                connectionDetails.region,
            R.string.home_country_label to
                connectionDetails.countryName,
            R.string.home_geolocation_label to
                connectionDetails.geolocation,
            R.string.home_post_code_label to
                connectionDetails.postCode,
            R.string.home_time_zone_label to
                connectionDetails.timeZone,
            R.string.home_internet_service_provider_label to
                connectionDetails.internetServiceProviderName
        ).forEach { (labelResourceId, fieldValue) ->
            if (fieldValue != null) {
                DetailsRow(
                    detailsItem = fieldValue,
                    labelResourceId = labelResourceId,
                    modifier = Modifier.padding(start = 16.dp, bottom = 2.dp)
                )
            }
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
            .background(colorScheme.surface)
    ) {
        ConnectedContent(
            connectionDetails = ConnectionDetailsUiModel(
                ipAddress = "0.0.0.0",
                city = IconLabelUiModel(R.drawable.icon_city, "Brentwood"),
                region = IconLabelUiModel(R.drawable.icon_region, "England"),
                countryName = IconLabelUiModel(R.drawable.icon_country, "GB"),
                geolocation = IconLabelUiModel(R.drawable.icon_geolocation, "0, 0"),
                postCode = IconLabelUiModel(R.drawable.icon_post_code, "AB12 3CD"),
                timeZone = IconLabelUiModel(R.drawable.icon_time_zone, "Europe/London"),
                internetServiceProviderName =
                IconLabelUiModel(R.drawable.icon_internet_service_provider, "TalkTalk")
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
