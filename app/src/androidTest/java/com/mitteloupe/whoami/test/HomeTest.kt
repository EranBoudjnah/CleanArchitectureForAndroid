package com.mitteloupe.whoami.test

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.intent.Intents
import com.mitteloupe.whoami.di.TestActivity
import com.mitteloupe.whoami.launcher.fromScreen
import com.mitteloupe.whoami.screen.HomeScreen
import com.mitteloupe.whoami.screen.OpenSourceNoticesScreen
import com.mitteloupe.whoami.server.REQUEST_RESPONSE_GET_IP
import com.mitteloupe.whoami.server.REQUEST_RESPONSE_GET_IP_DETAILS
import com.mitteloupe.whoami.test.annotation.ServerRequestResponse
import com.mitteloupe.whoami.test.launcher.AppLauncher
import com.mitteloupe.whoami.test.test.BaseTest
import com.mitteloupe.whoami.ui.main.route.Home
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Test

@HiltAndroidTest
@ExperimentalTestApi
class HomeTest : BaseTest() {
    override val composeTestRule = createAndroidComposeRule<TestActivity>()

    override val startActivityLauncher: AppLauncher by lazy {
        fromScreen(composeTestRule, Home)
    }

    @Inject
    lateinit var homeScreen: HomeScreen

    @Inject
    lateinit var openSourceNoticesScreen: OpenSourceNoticesScreen

    @Test
    @ServerRequestResponse([REQUEST_RESPONSE_GET_IP, REQUEST_RESPONSE_GET_IP_DETAILS])
    fun givenConnectedWhenStartingAppThenIpAddressPresented() {
        with(composeTestRule) {
            with(homeScreen) {
                seeIpAddressLabel()
                seeIpAddressSubtitleLabel()
            }
        }
    }

    @Test
    @ServerRequestResponse([REQUEST_RESPONSE_GET_IP, REQUEST_RESPONSE_GET_IP_DETAILS])
    fun givenConnectedWhenStartingAppThenIpAddressDetailsPresented() {
        with(composeTestRule) {
            with(homeScreen) {
                seeCityLabel()
                seeRegionLabel()
                seeCountryLabel()
                seeGeolocationLabel()
                seePostCodeLabel()
                seeTimeZoneLabel()
                seeInternetServiceProviderLabel()
            }
        }
    }

    @Test
    @ServerRequestResponse([REQUEST_RESPONSE_GET_IP, REQUEST_RESPONSE_GET_IP_DETAILS])
    fun whenTappingOnOpenSourceNoticesLaunchesActivity() {
        Intents.init()
        with(composeTestRule) {
            with(homeScreen) {
                tapOpenSourceNotices()
            }

            with(openSourceNoticesScreen) {
                seeScreen()
            }
        }
        Intents.release()
    }
}
