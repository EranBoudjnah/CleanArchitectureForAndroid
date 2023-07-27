package com.mitteloupe.whoami.ui.main

import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mitteloupe.whoami.history.ui.view.HistoryFragment
import com.mitteloupe.whoami.home.ui.Home
import com.mitteloupe.whoami.ui.main.model.AppNavHostDependencies

@Composable
fun AppNavHost(
    dependencies: AppNavHostDependencies,
    supportFragmentManager: FragmentManager,
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
                homeDestinationToUiMapper.setNavController(navController)

                Home(
                    homeViewModel,
                    connectionDetailsToUiMapper,
                    homeDestinationToUiMapper,
                    coroutineContextProvider
                )
            }
        }
        composable("history") {
            FragmentContainer(
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

@Composable
fun FragmentContainer(
    modifier: Modifier = Modifier,
    fragmentManager: FragmentManager,
    commit: FragmentTransaction.(containerId: Int) -> Unit,
    onFragmentViewCreated: (containerId: Int) -> Unit
) {
    val containerId by rememberSaveable { mutableStateOf(View.generateViewId()) }
    var initialized by rememberSaveable { mutableStateOf(false) }
    AndroidView(
        modifier = modifier,
        factory = { context ->
            FragmentContainerView(context)
                .apply { id = containerId }
        },
        update = { view ->
            if (!initialized) {
                fragmentManager.commit { commit(view.id) }
                initialized = true
            } else {
                fragmentManager.onContainerAvailable(view)
                onFragmentViewCreated(view.id)
            }
        }
    )
}

private fun FragmentManager.onContainerAvailable(view: FragmentContainerView) {
    val method = FragmentManager::class.java.getDeclaredMethod(
        "onContainerAvailable",
        FragmentContainerView::class.java
    )
    method.isAccessible = true
    method.invoke(this, view)
}
