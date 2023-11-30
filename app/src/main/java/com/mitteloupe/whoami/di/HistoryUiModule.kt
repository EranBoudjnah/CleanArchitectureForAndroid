package com.mitteloupe.whoami.di

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.mitteloupe.whoami.architecture.ui.binder.ViewStateBinder
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.DestinationToUiMapper
import com.mitteloupe.whoami.architecture.ui.view.ViewsProvider
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState
import com.mitteloupe.whoami.history.ui.adapter.HistoryAdapter
import com.mitteloupe.whoami.history.ui.binder.HistoryViewStateBinder
import com.mitteloupe.whoami.history.ui.mapper.HistoryRecordDeletionToPresentationMapper
import com.mitteloupe.whoami.history.ui.mapper.HistoryRecordToUiMapper
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
    fun providesNavHostController(fragment: Fragment): NavController =
        (fragment as HistoryFragment).navHostController

    @Provides
    @Named(HistoryFragment.NAVIGATION_MAPPER_NAME)
    fun providesHistoryDestinationToUiMapper(
        navControllerProvider: Provider<NavController>
    ): DestinationToUiMapper = HistoryDestinationToUiMapper { navControllerProvider.get() }

    @Provides
    fun providesHistoryRecordDeletionToPresentationMapper() =
        HistoryRecordDeletionToPresentationMapper()

    @Suppress("UNCHECKED_CAST")
    @Provides
    fun providesHistoryViewStateBinder(
        fragment: Fragment,
        historyRecordToUiMapper: HistoryRecordToUiMapper
    ) = HistoryViewStateBinder(
        fragment as HistoryAdapter.UserEventListener,
        historyRecordToUiMapper
    ) as ViewStateBinder<HistoryViewState, ViewsProvider>
}
