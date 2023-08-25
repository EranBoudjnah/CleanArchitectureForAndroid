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
import com.mitteloupe.whoami.ui.main.AppNavHost
import com.mitteloupe.whoami.ui.main.MainActivity
import com.mitteloupe.whoami.ui.main.model.AppNavHostDependencies
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
                AppNavHost(
                    AppNavHostDependencies(
                        homeViewModel = mock(),
                        homeDestinationToUiMapper = mock(),
                        connectionDetailsToUiMapper = mock(),
                        coroutineContextProvider = coroutineContextProvider,
                        analytics = analytics
                    ),
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
            seeIpRecord(ipAddress = "2.2.2.2", position = 1)
            seeLocation(city = "Stockholm", postCode = "12345", position = 1)
            seeIpRecord(ipAddress = "1.1.1.1", position = 2)
            seeLocation(city = "Aberdeen", postCode = "AA11 2BB", position = 2)
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
}
