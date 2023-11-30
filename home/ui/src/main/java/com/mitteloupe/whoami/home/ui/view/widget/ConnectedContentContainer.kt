package com.mitteloupe.whoami.home.ui.view.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.mitteloupe.whoami.home.ui.R
import com.mitteloupe.whoami.home.ui.content.ConnectedContent
import com.mitteloupe.whoami.home.ui.model.ConnectionDetailsUiModel
import com.mitteloupe.whoami.home.ui.model.IconLabelUiModel
import com.mitteloupe.whoami.home.ui.view.spring.enterSpring

@Composable
fun ConnectedContentContainer(visible: Boolean, connectionDetails: ConnectionDetailsUiModel?) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(
            animationSpec = enterSpring(IntSize.VisibilityThreshold)
        ) + slideInVertically(
            animationSpec = enterSpring(IntOffset.VisibilityThreshold)
        ) + fadeIn(),
        exit = shrinkVertically() + slideOutVertically() + fadeOut()
    ) {
        if (connectionDetails != null) {
            ConnectedContent(
                connectionDetails = connectionDetails,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ConnectedContentContainer(
        visible = true,
        connectionDetails = ConnectionDetailsUiModel(
            ipAddress = "8.8.8.8",
            city = IconLabelUiModel(R.drawable.icon_city, "Brentwood"),
            region = IconLabelUiModel(R.drawable.icon_region, "England"),
            countryName = IconLabelUiModel(R.drawable.icon_country, "GB"),
            geolocation = IconLabelUiModel(R.drawable.icon_geolocation, "0, 0"),
            postCode = IconLabelUiModel(R.drawable.icon_post_code, "AB12 3CD"),
            timeZone = IconLabelUiModel(R.drawable.icon_time_zone, "Europe/London"),
            internetServiceProviderName =
            IconLabelUiModel(R.drawable.icon_internet_service_provider, "TalkTalk")
        )
    )
}
