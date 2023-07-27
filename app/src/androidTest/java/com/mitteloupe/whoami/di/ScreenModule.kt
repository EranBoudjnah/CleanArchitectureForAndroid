package com.mitteloupe.whoami.di

import androidx.compose.ui.test.ExperimentalTestApi
import com.mitteloupe.whoami.screen.HistoryScreen
import com.mitteloupe.whoami.screen.HomeScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalTestApi
object ScreenModule {
    @Provides
    fun providesHomeScreen() = HomeScreen()

    @Provides
    fun providesHistoryScreen() = HistoryScreen()
}
