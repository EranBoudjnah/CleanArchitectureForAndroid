package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.opensourcenotices.ui.di.OpenSourceNoticesDependencies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object OpenSourceNoticesModule {
    @Provides
    fun providesOpenSourceNoticesDependencies(analytics: Analytics) =
        OpenSourceNoticesDependencies(analytics)
}
