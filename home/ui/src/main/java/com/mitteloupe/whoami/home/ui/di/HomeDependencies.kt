package com.mitteloupe.whoami.home.ui.di

import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.NavigationEventDestinationMapper
import com.mitteloupe.whoami.architecture.ui.notification.mapper.NotificationUiMapper
import com.mitteloupe.whoami.architecture.ui.view.BaseComposeHolder
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.presentation.model.HomePresentationNotification
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsUiMapper
import com.mitteloupe.whoami.home.ui.mapper.ErrorUiMapper
import com.mitteloupe.whoami.home.ui.mapper.HomeViewStateUiMapper

private typealias NavigationMapper = NavigationEventDestinationMapper<PresentationNavigationEvent>

data class HomeDependencies(
    val homeViewModel: HomeViewModel,
    val homeViewStateUiMapper: HomeViewStateUiMapper,
    val connectionDetailsUiMapper: ConnectionDetailsUiMapper,
    private val homeNavigationMapper: NavigationMapper,
    private val homeNotificationMapper: NotificationUiMapper,
    val errorUiMapper: ErrorUiMapper,
    val coroutineContextProvider: CoroutineContextProvider,
    val analytics: Analytics
) : BaseComposeHolder<HomeViewState, HomePresentationNotification>(
    homeViewModel,
    coroutineContextProvider,
    homeNavigationMapper,
    homeNotificationMapper
)
