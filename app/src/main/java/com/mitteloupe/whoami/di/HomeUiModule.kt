package com.mitteloupe.whoami.di

import android.content.Context
import android.content.res.Resources
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.NavigationEventToDestinationMapper
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.di.HomeDependencies
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsToUiMapper
import com.mitteloupe.whoami.home.ui.mapper.ErrorToUiMapper
import com.mitteloupe.whoami.home.ui.mapper.HomeNotificationToUiMapper
import com.mitteloupe.whoami.home.ui.mapper.HomeViewStateToUiMapper
import com.mitteloupe.whoami.ui.navigation.mapper.HomeNavigationEventToDestinationMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object HomeUiModule {
    @Provides
    fun providesHomeViewStateToUiMapper() = HomeViewStateToUiMapper()

    @Provides
    fun providesHomeDestinationToUiMapper(analytics: Analytics, @ActivityContext context: Context) =
        HomeNavigationEventToDestinationMapper(analytics, context)

    @Provides
    fun providesHomeNotificationToUiMapper(@ActivityContext context: Context) =
        HomeNotificationToUiMapper(context)

    @Provides
    fun providesErrorToUiMapper(resources: Resources) = ErrorToUiMapper(resources)

    @Provides
    fun providesConnectionDetailsToUiMapper() = ConnectionDetailsToUiMapper()

    @Provides
    @Suppress("UNCHECKED_CAST")
    fun providesHomeDependencies(
        homeViewModel: HomeViewModel,
        homeViewStateToUiMapper: HomeViewStateToUiMapper,
        connectionDetailsToUiMapper: ConnectionDetailsToUiMapper,
        homeNavigationMapper: HomeNavigationEventToDestinationMapper,
        homeNotificationMapper: HomeNotificationToUiMapper,
        errorToUiMapper: ErrorToUiMapper,
        coroutineContextProvider: CoroutineContextProvider,
        analytics: Analytics
    ) = HomeDependencies(
        homeViewModel,
        homeViewStateToUiMapper,
        connectionDetailsToUiMapper,
        homeNavigationMapper as NavigationEventToDestinationMapper<PresentationNavigationEvent>,
        homeNotificationMapper,
        errorToUiMapper,
        coroutineContextProvider,
        analytics
    )
}
