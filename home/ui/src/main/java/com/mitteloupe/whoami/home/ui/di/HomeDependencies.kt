package com.mitteloupe.whoami.home.ui.di

import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.NavigationEventToDestinationMapper
import com.mitteloupe.whoami.architecture.ui.notification.mapper.NotificationToUiMapper
import com.mitteloupe.whoami.architecture.ui.view.BaseComposeHolder
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.presentation.model.HomePresentationNotification
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsToUiMapper
import com.mitteloupe.whoami.home.ui.mapper.ErrorToUiMapper
import com.mitteloupe.whoami.home.ui.mapper.HomeViewStateToUiMapper

private typealias NavigationMapper = NavigationEventToDestinationMapper<PresentationNavigationEvent>

data class HomeDependencies(
    val homeViewModel: HomeViewModel,
    val homeViewStateToUiMapper: HomeViewStateToUiMapper,
    val connectionDetailsToUiMapper: ConnectionDetailsToUiMapper,
    private val homeNavigationMapper: NavigationMapper,
    private val homeNotificationMapper: NotificationToUiMapper,
    val errorToUiMapper: ErrorToUiMapper,
    val coroutineContextProvider: CoroutineContextProvider,
    val analytics: Analytics
) : BaseComposeHolder<HomeViewState, HomePresentationNotification>(
    homeViewModel,
    coroutineContextProvider,
    homeNavigationMapper,
    homeNotificationMapper
)
