package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.time.NowProvider
import com.mitteloupe.whoami.time.NowProvider.DefaultNowProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TimeModule {
    @Provides
    fun providesNowProvider(): NowProvider = DefaultNowProvider
}
