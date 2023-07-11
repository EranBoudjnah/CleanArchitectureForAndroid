package com.mitteloupe.whoami.home.ui.mapper

import androidx.annotation.DrawableRes
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.ui.R
import com.mitteloupe.whoami.home.ui.model.ConnectionDetailsUiModel
import com.mitteloupe.whoami.home.ui.model.IconLabelUiModel
import java.util.Locale

class ConnectionDetailsToUiMapper(
    private val toCountryName: String.() -> String = {
        val countryLocale = Locale("", this)
        countryLocale.displayCountry
    }
) {
    fun toUi(connectionDetails: HomeViewState.Connected) = ConnectionDetailsUiModel(
        ipAddress = connectionDetails.ipAddress,
        city = connectionDetails.city.labelAndIcon(R.drawable.icon_city),
        region = connectionDetails.region.labelAndIcon(R.drawable.icon_region),
        countryName = connectionDetails.countryCode?.toCountryName()
            .labelAndIcon(R.drawable.icon_country),
        geolocation = connectionDetails.geolocation?.replace(",", ", ")
            .labelAndIcon(R.drawable.icon_geolocation),
        postCode = connectionDetails.postCode.labelAndIcon(R.drawable.icon_post_code),
        timeZone = connectionDetails.timeZone.labelAndIcon(R.drawable.icon_time_zone),
        internetServiceProviderName = connectionDetails.internetServiceProviderName
            .labelAndIcon(R.drawable.icon_internet_service_provider)
    )

    private fun String?.labelAndIcon(@DrawableRes iconResourceId: Int) = this?.let {
        IconLabelUiModel(iconResourceId, this)
    }
}
