package com.mitteloupe.whoami.launcher

import androidx.appcompat.app.AppCompatActivity
import com.mitteloupe.whoami.di.testAppDependenciesEntryPoint
import com.mitteloupe.whoami.test.launcher.AppLauncher
import com.mitteloupe.whoami.test.launcher.fromComposable
import com.mitteloupe.whoami.test.test.TypedAndroidComposeTestRule
import com.mitteloupe.whoami.ui.main.AppNavHost
import com.mitteloupe.whoami.ui.theme.WhoAmITheme

fun <ACTIVITY : AppCompatActivity> fromScreen(
    composeContentTestRule: TypedAndroidComposeTestRule<ACTIVITY>,
    startDestination: Any
) = AppLauncher {
    fromComposable(composeContentTestRule) { activity ->
        WhoAmITheme {
            testAppDependenciesEntryPoint(activity).appNavHostDependencies.AppNavHost(
                supportFragmentManager = activity.supportFragmentManager,
                startDestination = startDestination
            )
        }
    }.launch()
}
