package com.mitteloupe.whoami.di

import androidx.fragment.app.Fragment
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.ui.binder.ViewStateBinder
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.NavigationEventDestinationMapper
import com.mitteloupe.whoami.architecture.ui.view.ViewsProvider
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState
import com.mitteloupe.whoami.history.ui.adapter.HistoryAdapter
import com.mitteloupe.whoami.history.ui.binder.HistoryViewStateBinder
import com.mitteloupe.whoami.history.ui.mapper.HistoryRecordDeletionPresentationMapper
import com.mitteloupe.whoami.history.ui.mapper.HistoryRecordUiMapper
import com.mitteloupe.whoami.history.ui.view.HistoryFragment
import com.mitteloupe.whoami.ui.navigation.mapper.HistoryNavigationEventDestinationMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Named

private typealias NavigationMapper = NavigationEventDestinationMapper<PresentationNavigationEvent>

@Module
@InstallIn(FragmentComponent::class)
object HistoryUiModule {
    @Provides
    @JvmSuppressWildcards
    @Named(HistoryFragment.NAVIGATION_MAPPER_NAME)
    fun providesHistoryNavigationEventDestinationMapper(): NavigationMapper =
        HistoryNavigationEventDestinationMapper()

    @Provides
    fun providesHistoryRecordDeletionPresentationMapper() =
        HistoryRecordDeletionPresentationMapper()

    @Suppress("UNCHECKED_CAST")
    @Provides
    fun providesHistoryViewStateBinder(
        fragment: Fragment,
        historyRecordUiMapper: HistoryRecordUiMapper
    ) = HistoryViewStateBinder(
        fragment as HistoryAdapter.UserEventListener,
        historyRecordUiMapper
    ) as ViewStateBinder<HistoryViewState, ViewsProvider>
}
