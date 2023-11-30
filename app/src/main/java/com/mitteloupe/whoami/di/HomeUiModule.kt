package com.mitteloupe.whoami.di

import android.content.Context
import android.content.res.Resources
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsToUiMapper
import com.mitteloupe.whoami.home.ui.mapper.ErrorToUiMapper
import com.mitteloupe.whoami.home.ui.mapper.HomeNotificationToUiMapper
import com.mitteloupe.whoami.ui.navigation.mapper.HomeDestinationToUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object HomeUiModule {
    @Provides
    fun providesHomeDestinationToUiMapper(analytics: Analytics, @ActivityContext context: Context) =
        HomeDestinationToUiMapper(analytics, context)

    @Provides
    fun providesHomeNotificationToUiMapper(@ActivityContext context: Context) =
        HomeNotificationToUiMapper(context)

    @Provides
    fun providesErrorToUiMapper(resources: Resources) = ErrorToUiMapper(resources)

    @Provides
    fun providesConnectionDetailsToUiMapper() = ConnectionDetailsToUiMapper()
}
