package com.mitteloupe.whoami.ui.main

import android.view.View
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentManager
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.mitteloupe.whoami.history.ui.view.HistoryFragment
import com.mitteloupe.whoami.home.ui.view.Home
import com.mitteloupe.whoami.ui.main.di.AppNavHostDependencies
import com.mitteloupe.whoami.ui.main.route.History
import com.mitteloupe.whoami.ui.main.route.Home

@Composable
fun AppNavHostDependencies.AppNavHost(
    supportFragmentManager: FragmentManager,
    startDestination: Any = Home
) {
    val containerId by rememberSaveable { mutableIntStateOf(View.generateViewId()) }

    val backStack = rememberSaveable { mutableStateListOf(startDestination) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Home> {
                homeDependencies.Home(backStack)
            }
            entry<History> { backStackEntry ->
                val history: History = backStackEntry
                FragmentContainer(
                    containerId = containerId,
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.statusBars),
                    fragmentManager = supportFragmentManager,
                    commit = { containerId ->
                        replace(
                            containerId,
                            HistoryFragment.newInstance(history.highlightedIpAddress)
                        )
                    },
                    onFragmentViewCreated = { containerId ->
                        val fragment = supportFragmentManager
                            .findFragmentById(containerId) as? HistoryFragment
                        fragment?.backStack = backStack
                    }
                )
            }
        }
    )
}
