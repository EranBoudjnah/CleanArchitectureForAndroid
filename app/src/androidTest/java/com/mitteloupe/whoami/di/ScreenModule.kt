package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.screen.HomeScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ScreenModule {
    @Provides
    fun providesHomeScreen() = HomeScreen()
}
