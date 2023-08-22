package com.mitteloupe.whoami.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.R
import com.mitteloupe.whoami.home.ui.content.ConnectedContent
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsToUiMapper
import com.mitteloupe.whoami.home.ui.model.ConnectionDetailsUiModel
import com.mitteloupe.whoami.home.ui.model.IconLabelUiModel
import com.mitteloupe.whoami.ui.main.model.AppNavHostDependencies
import com.mitteloupe.whoami.ui.navigation.mapper.HomeDestinationToUiMapper
import com.mitteloupe.whoami.ui.theme.WhoAmITheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var homeDestinationToUiMapper: HomeDestinationToUiMapper

    @Inject
    lateinit var coroutineContextProvider: CoroutineContextProvider

    @Inject
    lateinit var connectionDetailsToUiMapper: ConnectionDetailsToUiMapper

    @Inject
    lateinit var analytics: Analytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhoAmITheme {
                AppNavHost(
                    AppNavHostDependencies(
                        homeViewModel,
                        homeDestinationToUiMapper,
                        connectionDetailsToUiMapper,
                        coroutineContextProvider,
                        analytics
                    ),
                    supportFragmentManager
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WhoAmITheme {
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
            )
        )
    }
}
