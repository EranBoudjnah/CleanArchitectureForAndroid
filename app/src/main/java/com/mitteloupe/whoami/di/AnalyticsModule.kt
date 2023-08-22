package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.analytics.bogus.BogusAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {
    @Provides
    fun providesAnalytics(): Analytics = BogusAnalytics()
}
