package com.mitteloupe.whoami.ui.main.di

import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsToUiMapper
import com.mitteloupe.whoami.home.ui.mapper.ErrorToUiMapper
import com.mitteloupe.whoami.home.ui.mapper.HomeNotificationToUiMapper
import com.mitteloupe.whoami.ui.navigation.mapper.HomeDestinationToUiMapper

data class AppNavHostDependencies(
    val homeViewModel: HomeViewModel,
    val homeDestinationToUiMapper: HomeDestinationToUiMapper,
    val homeNotificationToUiMapper: HomeNotificationToUiMapper,
    val errorToUiMapper: ErrorToUiMapper,
    val connectionDetailsToUiMapper: ConnectionDetailsToUiMapper,
    val coroutineContextProvider: CoroutineContextProvider,
    val analytics: Analytics
)
