package com.mitteloupe.whoami.ui.main

import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mitteloupe.whoami.history.ui.view.HistoryFragment
import com.mitteloupe.whoami.home.ui.di.HomeDependencies
import com.mitteloupe.whoami.home.ui.view.Home
import com.mitteloupe.whoami.ui.main.di.AppNavHostDependencies

@Composable
fun AppNavHostDependencies.AppNavHost(
    supportFragmentManager: FragmentManager,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    val containerId by rememberSaveable { mutableIntStateOf(View.generateViewId()) }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("home") {
            homeDestinationToUiMapper.setNavController(navController)

            HomeDependencies(
                homeViewModel,
                connectionDetailsToUiMapper,
                homeDestinationToUiMapper,
                homeNotificationToUiMapper,
                errorToUiMapper,
                coroutineContextProvider,
                analytics
            ).Home()
        }
        composable("history") {
            FragmentContainer(
                containerId = containerId,
                modifier = Modifier.fillMaxSize(),
                fragmentManager = supportFragmentManager,
                commit = { containerId -> replace(containerId, HistoryFragment.newInstance()) },
                onFragmentViewCreated = { containerId ->
                    val fragment = supportFragmentManager
                        .findFragmentById(containerId) as? HistoryFragment
                    fragment?.setNavController(navController)
                }
            )
        }
    }
}
