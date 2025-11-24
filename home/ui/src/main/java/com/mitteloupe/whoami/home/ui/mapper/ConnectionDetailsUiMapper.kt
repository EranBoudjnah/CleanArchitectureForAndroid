package com.mitteloupe.whoami.home.ui.mapper

import androidx.annotation.DrawableRes
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.ui.R
import com.mitteloupe.whoami.home.ui.model.ConnectionDetailsUiModel
import com.mitteloupe.whoami.home.ui.model.IconLabelUiModel
import java.util.Locale

class ConnectionDetailsUiMapper {
    fun toUi(connectionDetails: HomeViewState.Connected) = ConnectionDetailsUiModel(
        ipAddress = connectionDetails.ipAddress,
        cityIconLabel = connectionDetails.city.labelAndIcon(R.drawable.icon_city),
        regionIconLabel = connectionDetails.region.labelAndIcon(R.drawable.icon_region),
        countryIconLabel = connectionDetails.countryCode?.toCountryName()
            .labelAndIcon(R.drawable.icon_country),
        geolocationIconLabel = connectionDetails.geolocation?.replace(",", ", ")
            .labelAndIcon(R.drawable.icon_geolocation),
        postCode = connectionDetails.postCode.labelAndIcon(R.drawable.icon_post_code),
        timeZone = connectionDetails.timeZone.labelAndIcon(R.drawable.icon_time_zone),
        internetServiceProviderName = connectionDetails.internetServiceProviderName
            .labelAndIcon(R.drawable.icon_internet_service_provider)
    )

    private fun String?.labelAndIcon(@DrawableRes iconResourceId: Int) = this?.let {
        IconLabelUiModel(iconResourceId, this)
    }

    private fun String.toCountryName() = Locale.Builder().setRegion(this).build().displayCountry
}
