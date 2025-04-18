package com.mitteloupe.whoami.test

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.mitteloupe.whoami.di.TestActivity
import com.mitteloupe.whoami.launcher.fromScreen
import com.mitteloupe.whoami.localstore.KEY_VALUE_NO_HISTORY
import com.mitteloupe.whoami.localstore.KEY_VALUE_SAVED_HISTORY
import com.mitteloupe.whoami.screen.HistoryScreen
import com.mitteloupe.whoami.test.annotation.LocalStore
import com.mitteloupe.whoami.test.launcher.AppLauncher
import com.mitteloupe.whoami.test.test.BaseTest
import com.mitteloupe.whoami.test.test.doesNot
import com.mitteloupe.whoami.test.test.retry
import com.mitteloupe.whoami.ui.main.route.History
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Test

@HiltAndroidTest
@ExperimentalTestApi
class HistoryTest : BaseTest() {
    override val composeTestRule = createAndroidComposeRule<TestActivity>()

    override val startActivityLauncher: AppLauncher by lazy {
        fromScreen(composeTestRule, History(highlightedIpAddress = null))
    }

    @Inject
    lateinit var historyScreen: HistoryScreen

    @Test
    @LocalStore(localStoreDataIds = [KEY_VALUE_SAVED_HISTORY])
    fun givenSavedHistoryWhenOnHistoryScreenThenSeesHistory() {
        with(historyScreen) {
            seeRecord(position = 1, ipAddress = "2.2.2.2", city = "Stockholm", postCode = "12345")
            seeRecord(position = 2, ipAddress = "1.1.1.1", city = "Aberdeen", postCode = "AA11 2BB")
        }
    }

    @Test
    @LocalStore(localStoreDataIds = [KEY_VALUE_SAVED_HISTORY])
    fun givenSavedHistoryWhenTappingDeleteThenRecordDeleted() {
        with(historyScreen) {
            retry(repeat = 20) {
                seeRecord(
                    position = 1,
                    ipAddress = "2.2.2.2",
                    city = "Stockholm",
                    postCode = "12345"
                )
            }
            seeRecord(position = 2, ipAddress = "1.1.1.1", city = "Aberdeen", postCode = "AA11 2BB")
            tapDeleteForRecord(position = 1)
            retry(repeat = 20) {
                seeRecord(
                    position = 1,
                    ipAddress = "1.1.1.1",
                    city = "Aberdeen",
                    postCode = "AA11 2BB"
                )
            }
            doesNot("See record for 2.2.2.2") {
                seeRecord(
                    position = 2,
                    ipAddress = "2.2.2.2",
                    city = "Stockholm",
                    postCode = "12345"
                )
            }
        }
    }

    @Test
    @LocalStore(localStoreDataIds = [KEY_VALUE_NO_HISTORY])
    fun givenNoHistoryWhenOnHistoryScreenThenSeesNoRecords() {
        with(historyScreen) {
            retry(repeat = 20) {
                seeNoRecordsLabel()
            }
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
