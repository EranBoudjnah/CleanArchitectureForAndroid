package com.mitteloupe.whoami.test

import androidx.compose.ui.test.ExperimentalTestApi
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
import com.mitteloupe.whoami.ui.theme.WhoAmITheme
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Test

@HiltAndroidTest
@ExperimentalTestApi
class HomeTest : BaseTest() {
    override val startActivityLauncher: AppLauncher by lazy {
        FromComposable(
            composeTestRule
        ) {
            WhoAmITheme {
                AppNavHost(homeViewModel, connectionDetailsToUiMapper, coroutineContextProvider)
            }
        }
    } //        ActivityLauncher.FromClass(MainActivity::class.java)

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
