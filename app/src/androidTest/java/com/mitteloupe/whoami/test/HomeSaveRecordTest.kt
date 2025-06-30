package com.mitteloupe.whoami.test

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.mitteloupe.whoami.di.TestActivity
import com.mitteloupe.whoami.launcher.fromScreen
import com.mitteloupe.whoami.screen.HistoryScreen
import com.mitteloupe.whoami.screen.HomeScreen
import com.mitteloupe.whoami.server.REQUEST_RESPONSE_GET_IP
import com.mitteloupe.whoami.server.REQUEST_RESPONSE_GET_IP_DETAILS
import com.mitteloupe.whoami.test.annotation.ServerRequestResponse
import com.mitteloupe.whoami.test.launcher.AppLauncher
import com.mitteloupe.whoami.test.test.BaseTest
import com.mitteloupe.whoami.test.test.retry
import com.mitteloupe.whoami.ui.main.route.Home
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Test

@HiltAndroidTest
@ExperimentalTestApi
class HomeSaveRecordTest : BaseTest() {
    override val composeTestRule = createAndroidComposeRule<TestActivity>()

    override val startActivityLauncher: AppLauncher by lazy {
        fromScreen(composeTestRule, Home)
    }

    @Inject
    lateinit var homeScreen: HomeScreen

    @Inject
    lateinit var historyScreen: HistoryScreen

    @Test
    @ServerRequestResponse([REQUEST_RESPONSE_GET_IP, REQUEST_RESPONSE_GET_IP_DETAILS])
    fun givenConnectedWhenNavigatingAwayAndBackThenSavesRecord() {
        with(composeTestRule) {
            with(homeScreen) {
                seeCityLabel()
                tapSaveDetailsButton()
            }

            with(historyScreen) {
                retry(repeat = 20) {
                    seeRecord(
                        position = 1,
                        ipAddress = "1.2.3.4",
                        city = "Brentwood",
                        postCode = "CM14"
                    )
                }
                tapBackButton()
            }

            with(homeScreen) {
                seeCityLabel()
                tapSaveDetailsButton()
            }

            with(historyScreen) {
                retry(repeat = 20) {
                    seeRecord(
                        position = 1,
                        ipAddress = "1.2.3.4",
                        city = "Brentwood",
                        postCode = "CM14"
                    )
                }
            }
        }
    }
}
