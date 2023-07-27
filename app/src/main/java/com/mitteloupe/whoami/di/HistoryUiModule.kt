package com.mitteloupe.whoami.di

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.DestinationToUiMapper
import com.mitteloupe.whoami.history.ui.view.HistoryFragment
import com.mitteloupe.whoami.ui.navigation.mapper.HistoryDestinationToUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Named
import javax.inject.Provider

@Module
@InstallIn(FragmentComponent::class)
object HistoryUiModule {
    @Provides
    fun providesNavHostController(
        fragment: Fragment
    ): NavController = (fragment as HistoryFragment).navHostController

    @Provides
    @Named(HistoryFragment.NAVIGATION_MAPPER_NAME)
    fun providesHistoryDestinationToUiMapper(
        navControllerProvider: Provider<NavController>
    ): DestinationToUiMapper =
        HistoryDestinationToUiMapper { navControllerProvider.get() }
}
