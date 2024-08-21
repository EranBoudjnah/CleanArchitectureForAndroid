package com.mitteloupe.whoami.launcher

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import com.mitteloupe.whoami.di.testAppDependenciesEntryPoint
import com.mitteloupe.whoami.test.test.BaseTest
import com.mitteloupe.whoami.test.test.BaseTest.AppLauncher.FromComposable
import com.mitteloupe.whoami.ui.main.AppNavHost
import com.mitteloupe.whoami.ui.main.MainActivity
import com.mitteloupe.whoami.ui.main.route.History
import com.mitteloupe.whoami.ui.theme.WhoAmITheme
import org.junit.rules.TestRule

fun BaseTest.historyScreenLauncher(highlightedIpAddress: String?): FromComposable {
    @Suppress("UNCHECKED_CAST")
    val composeContentTestRule =
        composeTestRule as AndroidComposeTestRule<TestRule, ComponentActivity>
    return FromComposable(composeContentTestRule) {
        WhoAmITheme {
            val activity = LocalContext.current as MainActivity
            with(testAppDependenciesEntryPoint(activity).appNavHostDependencies) {
                AppNavHost(
                    activity.supportFragmentManager,
                    startDestination = History(highlightedIpAddress = highlightedIpAddress)
                )
            }
        }
    }
}
