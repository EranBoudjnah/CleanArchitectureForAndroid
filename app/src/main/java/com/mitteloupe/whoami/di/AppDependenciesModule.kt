package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.home.ui.di.HomeDependencies
import com.mitteloupe.whoami.opensourcenotices.ui.di.OpenSourceNoticesDependencies
import com.mitteloupe.whoami.ui.main.di.AppNavHostDependencies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Provider

@Module
@InstallIn(ActivityComponent::class)
object AppDependenciesModule {
    @Provides
    fun providesAppNavHostDependencies(
        homeDependencies: HomeDependencies,
        openSourceNoticesDependenciesProvider: Provider<OpenSourceNoticesDependencies>
    ): AppNavHostDependencies = AppNavHostDependencies(
        homeDependencies,
        lazy { openSourceNoticesDependenciesProvider.get() }
    )
}
