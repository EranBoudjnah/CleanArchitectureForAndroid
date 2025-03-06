package com.mitteloupe.whoami.home.ui.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mitteloupe.whoami.home.ui.R
import com.mitteloupe.whoami.home.ui.model.ConnectionDetailsUiModel
import com.mitteloupe.whoami.home.ui.model.DetailsUiModel
import com.mitteloupe.whoami.home.ui.model.IconLabelUiModel

@Composable
fun DetailsBlock(connectionDetails: ConnectionDetailsUiModel, modifier: Modifier = Modifier) {
    val resources = LocalContext.current.resources
    val detailsContainer by remember(connectionDetails) {
        mutableStateOf(
            DetailsUiModel(
                listOf(
                    R.string.home_city_label to connectionDetails.cityIconLabel,
                    R.string.home_region_label to connectionDetails.regionIconLabel,
                    R.string.home_country_label to connectionDetails.countryIconLabel,
                    R.string.home_geolocation_label to connectionDetails.geolocationIconLabel,
                    R.string.home_post_code_label to connectionDetails.postCode,
                    R.string.home_time_zone_label to connectionDetails.timeZone,
                    R.string.home_internet_service_provider_label to
                        connectionDetails.internetServiceProviderName
                ).map { resourceIdLabelPair ->
                    resources.getString(resourceIdLabelPair.first) to resourceIdLabelPair.second
                }
            )
        )
    }

    Column(modifier = modifier.padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 0.dp)) {
        Text(
            text = stringResource(R.string.home_details_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        detailsContainer.details
            .mapNotNull { (labelResourceId, fieldValue) ->
                fieldValue?.let { labelResourceId to fieldValue }
            }.forEach { (label, fieldValue) ->
                DetailsRow(
                    label = label,
                    detailsItem = fieldValue,
                    modifier = Modifier.padding(start = 16.dp, bottom = 2.dp)
                )
            }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun Preview() {
    DetailsBlock(
        connectionDetails = ConnectionDetailsUiModel(
            ipAddress = "8.8.8.8",
            cityIconLabel = IconLabelUiModel(R.drawable.icon_city, "Tatatown"),
            regionIconLabel = IconLabelUiModel(R.drawable.icon_region, "Lalaland"),
            countryIconLabel = IconLabelUiModel(R.drawable.icon_country, "LA"),
            geolocationIconLabel = IconLabelUiModel(R.drawable.icon_geolocation, "0, 0"),
            postCode = IconLabelUiModel(R.drawable.icon_post_code, "AB12 3CD"),
            timeZone = IconLabelUiModel(R.drawable.icon_time_zone, "Europe/London"),
            internetServiceProviderName =
            IconLabelUiModel(R.drawable.icon_internet_service_provider, "TalkTalk")
        )
    )
}
