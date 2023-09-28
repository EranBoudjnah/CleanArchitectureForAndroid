package com.mitteloupe.whoami.home.ui.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mitteloupe.whoami.home.ui.R
import com.mitteloupe.whoami.home.ui.model.ConnectionDetailsUiModel
import com.mitteloupe.whoami.home.ui.model.DetailsUiModel
import com.mitteloupe.whoami.home.ui.model.IconLabelUiModel

@Composable
fun DetailsBlock(
    connectionDetails: ConnectionDetailsUiModel,
    modifier: Modifier = Modifier,
    detailsContainer: DetailsUiModel = DetailsUiModel(
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
        ).map { resourceIdLabelPair ->
            stringResource(resourceIdLabelPair.first) to resourceIdLabelPair.second
        }
    )
) {
    Column(
        modifier = modifier.padding(16.dp, 24.dp, 16.dp, 0.dp)
    ) {
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
            city = IconLabelUiModel(R.drawable.icon_city, "Tatatown"),
            region = IconLabelUiModel(R.drawable.icon_region, "Lalaland"),
            countryName = IconLabelUiModel(R.drawable.icon_country, "LA"),
            geolocation = IconLabelUiModel(R.drawable.icon_geolocation, "0, 0"),
            postCode = IconLabelUiModel(R.drawable.icon_post_code, "AB12 3CD"),
            timeZone = IconLabelUiModel(R.drawable.icon_time_zone, "Europe/London"),
            internetServiceProviderName =
            IconLabelUiModel(R.drawable.icon_internet_service_provider, "TalkTalk")
        )
    )
}
