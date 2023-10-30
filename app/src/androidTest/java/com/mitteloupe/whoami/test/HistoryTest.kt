package com.mitteloupe.whoami.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.localstore.KEY_VALUE_NO_HISTORY
import com.mitteloupe.whoami.localstore.KEY_VALUE_SAVED_HISTORY
import com.mitteloupe.whoami.screen.HistoryScreen
import com.mitteloupe.whoami.test.annotation.LocalStore
import com.mitteloupe.whoami.test.test.BaseTest
import com.mitteloupe.whoami.test.test.BaseTest.AppLauncher.FromComposable
import com.mitteloupe.whoami.test.test.doesNot
import com.mitteloupe.whoami.test.test.retry
import com.mitteloupe.whoami.ui.main.AppNavHost
import com.mitteloupe.whoami.ui.main.MainActivity
import com.mitteloupe.whoami.ui.main.di.AppNavHostDependencies
import com.mitteloupe.whoami.ui.theme.WhoAmITheme
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.mock

@HiltAndroidTest
@ExperimentalTestApi
class HistoryTest : BaseTest() {
    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    override val startActivityLauncher: AppLauncher by lazy {
        @Suppress("UNCHECKED_CAST")
        val composeContentTestRule =
            composeTestRule as AndroidComposeTestRule<TestRule, ComponentActivity>
        FromComposable(composeContentTestRule) {
            WhoAmITheme {
                val activity = LocalContext.current as MainActivity
                AppNavHostDependencies(
                    homeViewModel = mock(),
                    homeDestinationToUiMapper = mock(),
                    homeNotificationToUiMapper = mock(),
                    connectionDetailsToUiMapper = mock(),
                    errorToUiMapper = mock(),
                    coroutineContextProvider = coroutineContextProvider,
                    analytics = analytics
                ).AppNavHost(
                    activity.supportFragmentManager,
                    startDestination = "history"
                )
            }
        }
    }

    @Inject
    lateinit var historyScreen: HistoryScreen

    @Inject
    lateinit var coroutineContextProvider: CoroutineContextProvider

    @Inject
    lateinit var analytics: Analytics

    @Test
    @LocalStore(
        localStoreDataIds = [KEY_VALUE_SAVED_HISTORY]
    )
    fun givenSavedHistoryWhenOnHistoryScreenThenSeesHistory() {
        with(historyScreen) {
            seeRecord(position = 1, ipAddress = "2.2.2.2", city = "Stockholm", postCode = "12345")
            seeRecord(position = 2, ipAddress = "1.1.1.1", city = "Aberdeen", postCode = "AA11 2BB")
        }
    }

    @Test
    @LocalStore(
        localStoreDataIds = [KEY_VALUE_SAVED_HISTORY]
    )
    fun givenSavedHistoryWhenTappingDeleteThenRecordDeleted() {
        with(historyScreen) {
            retry {
                seeRecord(
                    position = 1,
                    ipAddress = "2.2.2.2",
                    city = "Stockholm",
                    postCode = "12345"
                )
            }
            seeRecord(position = 2, ipAddress = "1.1.1.1", city = "Aberdeen", postCode = "AA11 2BB")
            tapDeleteForRecord(position = 1)
            retry {
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
    @LocalStore(
        localStoreDataIds = [KEY_VALUE_NO_HISTORY]
    )
    fun givenNoHistoryWhenOnHistoryScreenThenSeesHistory() {
        with(historyScreen) {
            seeNoRecordsLabel()
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
