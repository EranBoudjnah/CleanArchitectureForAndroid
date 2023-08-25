package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsToUiMapper
import com.mitteloupe.whoami.ui.main.model.AppNavHostDependencies
import com.mitteloupe.whoami.ui.navigation.mapper.HomeDestinationToUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object HomeTestModule {
    @Provides
    fun providesAppNavHostDependencies(
        homeViewModel: HomeViewModel,
        homeDestinationToUiMapper: HomeDestinationToUiMapper,
        connectionDetailsToUiMapper: ConnectionDetailsToUiMapper,
        coroutineContextProvider: CoroutineContextProvider,
        analytics: Analytics
    ): AppNavHostDependencies = AppNavHostDependencies(
        homeViewModel,
        homeDestinationToUiMapper,
        connectionDetailsToUiMapper,
        coroutineContextProvider,
        analytics
    )
}
