package com.mitteloupe.whoami.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mitteloupe.whoami.home.ui.Home
import com.mitteloupe.whoami.ui.main.model.AppNavHostDependencies

@Composable
fun AppNavHost(
    dependencies: AppNavHostDependencies,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("home") {
            with(dependencies) {
                Home(homeViewModel, connectionDetailsToUiMapper, coroutineContextProvider)
            }
        }
    }
}
