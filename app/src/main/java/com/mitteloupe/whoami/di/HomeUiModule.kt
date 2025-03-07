package com.mitteloupe.whoami.di

import android.content.Context
import android.content.res.Resources
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.NavigationEventDestinationMapper
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.di.HomeDependencies
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
    fun providesHomeNavigationEventDestinationMapper(
        analytics: Analytics,
        @ActivityContext context: Context
    ) = HomeNavigationEventDestinationMapper(analytics, context)

    @Provides
    fun providesHomeNotificationUiMapper(@ActivityContext context: Context) =
        HomeNotificationUiMapper(context)

    @Provides
    fun providesErrorUiMapper(resources: Resources) = ErrorUiMapper(resources)

    @Provides
    fun providesConnectionDetailsUiMapper() = ConnectionDetailsUiMapper()

    @Provides
    @Suppress("UNCHECKED_CAST")
    fun providesHomeDependencies(
        homeViewModel: HomeViewModel,
        homeViewStateUiMapper: HomeViewStateUiMapper,
        connectionDetailsUiMapper: ConnectionDetailsUiMapper,
        homeNavigationMapper: HomeNavigationEventDestinationMapper,
        homeNotificationMapper: HomeNotificationUiMapper,
        errorUiMapper: ErrorUiMapper,
        analytics: Analytics
    ) = HomeDependencies(
        homeViewModel,
        homeViewStateUiMapper,
        connectionDetailsUiMapper,
        homeNavigationMapper as NavigationEventDestinationMapper<PresentationNavigationEvent>,
        homeNotificationMapper,
        errorUiMapper,
        analytics
    )
}
