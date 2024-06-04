package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.home.ui.di.HomeDependencies
import com.mitteloupe.whoami.ui.main.di.AppNavHostDependencies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object AppDependenciesModule {
    @Provides
    fun providesAppNavHostDependencies(homeDependencies: HomeDependencies): AppNavHostDependencies =
        AppNavHostDependencies(homeDependencies)
}
