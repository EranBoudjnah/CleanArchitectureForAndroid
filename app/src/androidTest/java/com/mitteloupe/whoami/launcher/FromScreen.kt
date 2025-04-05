package com.mitteloupe.whoami.launcher

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.appcompat.app.AppCompatActivity
import com.mitteloupe.whoami.di.testAppDependenciesEntryPoint
import com.mitteloupe.whoami.test.test.BaseTest.AppLauncher
import com.mitteloupe.whoami.test.test.TypedAndroidComposeTestRule
import com.mitteloupe.whoami.ui.main.AppNavHost
import com.mitteloupe.whoami.ui.theme.WhoAmITheme

data class FromScreen<ACTIVITY : ComponentActivity>(
    private val composeContentTestRule: TypedAndroidComposeTestRule<ACTIVITY>,
    private val startDestination: Any
) : AppLauncher() {
    private val composableAppLauncher = FromComposable(composeContentTestRule) {
        WhoAmITheme {
            val activity = LocalActivity.current as AppCompatActivity
            with(testAppDependenciesEntryPoint(activity).appNavHostDependencies) {
                AppNavHost(
                    supportFragmentManager = activity.supportFragmentManager,
                    startDestination = startDestination
                )
            }
        }
    }

    override fun launch() = composableAppLauncher.launch()
}
