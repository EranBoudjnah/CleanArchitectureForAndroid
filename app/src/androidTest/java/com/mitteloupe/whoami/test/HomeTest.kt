package com.mitteloupe.whoami.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.intent.Intents
import com.mitteloupe.whoami.screen.HomeScreen
import com.mitteloupe.whoami.screen.OpenSourceNoticesScreen
import com.mitteloupe.whoami.server.REQUEST_RESPONSE_GET_IP
import com.mitteloupe.whoami.server.REQUEST_RESPONSE_GET_IP_DETAILS
import com.mitteloupe.whoami.test.annotation.ServerRequestResponse
import com.mitteloupe.whoami.test.test.BaseTest
import com.mitteloupe.whoami.test.test.BaseTest.AppLauncher.FromComposable
import com.mitteloupe.whoami.ui.main.AppNavHost
import com.mitteloupe.whoami.ui.main.MainActivity
import com.mitteloupe.whoami.ui.main.model.AppNavHostDependencies
import com.mitteloupe.whoami.ui.theme.WhoAmITheme
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.rules.TestRule

@HiltAndroidTest
@ExperimentalTestApi
class HomeTest : BaseTest() {
    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ActivityEntryPoint {
        fun appNavHostDependencies(): AppNavHostDependencies
    }

    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    override val startActivityLauncher: AppLauncher by lazy {
        @Suppress("UNCHECKED_CAST")
        val composeContentTestRule =
            composeTestRule as AndroidComposeTestRule<TestRule, ComponentActivity>
        FromComposable(composeContentTestRule) {
            WhoAmITheme {
                val activity = LocalContext.current as MainActivity
                EntryPoints.get(activity, ActivityEntryPoint::class.java).appNavHostDependencies()
                    .AppNavHost(
                        activity.supportFragmentManager
                    )
            }
        }
    }

    @Inject
    lateinit var homeScreen: HomeScreen

    @Inject
    lateinit var openSourceNoticesScreen: OpenSourceNoticesScreen

    @Before
    override fun setUp() {
        super.setUp()
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    @ServerRequestResponse(
        [
            REQUEST_RESPONSE_GET_IP,
            REQUEST_RESPONSE_GET_IP_DETAILS
        ]
    )
    fun givenConnectedWhenStartingAppThenIpAddressPresented() {
        with(composeTestRule) {
            with(homeScreen) {
                seesIpAddressLabel()
                seesIpAddressSubtitleLabel()
            }
        }
    }

    @Test
    @ServerRequestResponse(
        [
            REQUEST_RESPONSE_GET_IP,
            REQUEST_RESPONSE_GET_IP_DETAILS
        ]
    )
    fun givenConnectedWhenStartingAppThenIpAddressDetailsPresented() {
        with(composeTestRule) {
            with(homeScreen) {
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

    @Test
    fun whenTappingOnOpenSourceNoticesLaunchesActivity() {
        with(composeTestRule) {
            with(homeScreen) {
                tapOpenSourceNotices()
            }

            with(openSourceNoticesScreen) {
                seesScreen()
            }
        }
    }
}
