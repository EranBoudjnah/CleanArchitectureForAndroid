package com.mitteloupe.whoami.test

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.mitteloupe.whoami.di.TestActivity
import com.mitteloupe.whoami.launcher.fromScreen
import com.mitteloupe.whoami.localstore.KEY_VALUE_SAVED_HISTORY
import com.mitteloupe.whoami.screen.HistoryScreen
import com.mitteloupe.whoami.test.annotation.LocalStore
import com.mitteloupe.whoami.test.launcher.AppLauncher
import com.mitteloupe.whoami.test.test.BaseTest
import com.mitteloupe.whoami.test.test.retry
import com.mitteloupe.whoami.ui.main.route.History
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Test

private const val HIGHLIGHTED_IP_ADDRESS = "2.2.2.2"

@HiltAndroidTest
@ExperimentalTestApi
class HistoryHighlightedIpAddressTest : BaseTest() {
    override val composeTestRule = createAndroidComposeRule<TestActivity>()

    override val startActivityLauncher: AppLauncher by lazy {
        fromScreen(composeTestRule, History(highlightedIpAddress = HIGHLIGHTED_IP_ADDRESS))
    }

    @Inject
    lateinit var historyScreen: HistoryScreen

    @Test
    @LocalStore(localStoreDataIds = [KEY_VALUE_SAVED_HISTORY])
    fun givenSavedHistoryAndHighlightedIpAddressWhenOnHistoryScreenTheSeesHighlight() {
        with(historyScreen) {
            retry(repeat = 20) {
                seeRecord(
                    position = 1,
                    ipAddress = HIGHLIGHTED_IP_ADDRESS,
                    city = "Stockholm",
                    postCode = "12345"
                )
            }
            seeHighlightedRecord(ipAddress = HIGHLIGHTED_IP_ADDRESS, position = 1)
            val ipAddress2 = "1.1.1.1"
            seeRecord(
                position = 2,
                ipAddress = ipAddress2,
                city = "Aberdeen",
                postCode = "AA11 2BB"
            )
            seeNonHighlightedRecord(ipAddress = ipAddress2, position = 2)
        }
    }

    private fun HistoryScreen.seeRecord(
        position: Int,
        ipAddress: String,
        city: String,
        postCode: String
    ) {
        seeIpRecord(ipAddress = ipAddress, position = position)
        seeLocation(city = city, postCode = postCode, position = position)
    }
}
