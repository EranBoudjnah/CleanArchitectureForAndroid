package com.mitteloupe.whoami.test

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsToUiMapper
import com.mitteloupe.whoami.screen.HomeScreen
import com.mitteloupe.whoami.server.REQUEST_RESPONSE_GET_IP
import com.mitteloupe.whoami.server.REQUEST_RESPONSE_GET_IP_DETAILS
import com.mitteloupe.whoami.test.annotation.ServerRequestResponse
import com.mitteloupe.whoami.test.test.BaseTest
import com.mitteloupe.whoami.test.test.BaseTest.AppLauncher.FromComposable
import com.mitteloupe.whoami.ui.main.AppNavHost
import com.mitteloupe.whoami.ui.main.MainActivity
import com.mitteloupe.whoami.ui.main.model.AppNavHostDependencies
import com.mitteloupe.whoami.ui.theme.WhoAmITheme
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Test

@HiltAndroidTest
@ExperimentalTestApi
class HomeTest : BaseTest() {
    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    override val startActivityLauncher: AppLauncher by lazy {
        FromComposable(
            composeTestRule
        ) {
            WhoAmITheme {
                val activity = LocalContext.current as MainActivity
                AppNavHost(
                    AppNavHostDependencies(
                        homeViewModel,
                        connectionDetailsToUiMapper,
                        coroutineContextProvider
                    ),
                    activity.supportFragmentManager
                )
            }
        }
    }

    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var coroutineContextProvider: CoroutineContextProvider

    @Inject
    lateinit var connectionDetailsToUiMapper: ConnectionDetailsToUiMapper

    @Inject
    lateinit var homeScreen: HomeScreen

    @Test
    @ServerRequestResponse(
        [
            REQUEST_RESPONSE_GET_IP,
            REQUEST_RESPONSE_GET_IP_DETAILS
        ]
    )
    fun givenConnectedWhenStartingAppThenIpAddressAndDetailsPresented() {
        with(composeTestRule) {
            with(homeScreen) {
                seesIpAddressLabel()
                seesIpAddressSubtitleLabel()
                seesCityLabel()
                seesRegionLabel()
                seesCountryLabel()
                seesGeolocationLabel()
                seesPostCodeLabel()
                seesTimeZoneLabel()
                seesInternetServiceProviderLabel()
            }
        }
    }
}
