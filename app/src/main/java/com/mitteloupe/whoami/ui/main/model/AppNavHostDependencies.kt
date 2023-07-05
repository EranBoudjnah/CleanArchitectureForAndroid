package com.mitteloupe.whoami.ui.main.model

import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsToUiMapper

data class AppNavHostDependencies(
    val homeViewModel: HomeViewModel,
    val connectionDetailsToUiMapper: ConnectionDetailsToUiMapper,
    val coroutineContextProvider: CoroutineContextProvider
)
