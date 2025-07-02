package com.mitteloupe.whoami.di

import android.content.Context
import android.content.res.Resources
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.NavigationEventDestinationMapper
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.di.HomeDependencies
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsPresentationMapper
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsUiMapper
import com.mitteloupe.whoami.home.ui.mapper.ErrorUiMapper
import com.mitteloupe.whoami.home.ui.mapper.HomeNotificationUiMapper
import com.mitteloupe.whoami.home.ui.mapper.HomeViewStateUiMapper
import com.mitteloupe.whoami.navigation.mapper.HomeNavigationEventDestinationMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object HomeUiModule {
    @Provides
    fun providesHomeViewStateUiMapper() = HomeViewStateUiMapper()

    @Provides
    @JvmSuppressWildcards
    fun providesHomeNavigationEventDestinationMapper(
        analytics: Analytics,
        @ActivityContext context: Context
    ): NavigationEventDestinationMapper<HomePresentationNavigationEvent> =
        HomeNavigationEventDestinationMapper(analytics, context)

    @Provides
    fun providesHomeNotificationUiMapper(@ActivityContext context: Context) =
        HomeNotificationUiMapper(context)

    @Provides
    fun providesErrorUiMapper(resources: Resources) = ErrorUiMapper(resources)

    @Provides
    fun providesConnectionDetailsPresentationMapper() = ConnectionDetailsPresentationMapper()

    @Provides
    fun providesConnectionDetailsUiMapper() = ConnectionDetailsUiMapper()

    @Provides
    fun providesHomeDependencies(
        homeViewModel: HomeViewModel,
        homeViewStateUiMapper: HomeViewStateUiMapper,
        connectionDetailsPresentationMapper: ConnectionDetailsPresentationMapper,
        connectionDetailsUiMapper: ConnectionDetailsUiMapper,
        homeNavigationMapper: @JvmSuppressWildcards
        NavigationEventDestinationMapper<HomePresentationNavigationEvent>,
        homeNotificationMapper: HomeNotificationUiMapper,
        errorUiMapper: ErrorUiMapper,
        analytics: Analytics
    ) = HomeDependencies(
        homeViewModel,
        homeViewStateUiMapper,
        connectionDetailsPresentationMapper,
        connectionDetailsUiMapper,
        homeNavigationMapper,
        homeNotificationMapper,
        errorUiMapper,
        analytics
    )
}
