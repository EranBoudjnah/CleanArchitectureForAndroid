package com.mitteloupe.whoami.home.ui.di

import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.NavigationEventDestinationMapper
import com.mitteloupe.whoami.architecture.ui.notification.mapper.NotificationUiMapper
import com.mitteloupe.whoami.architecture.ui.view.BaseComposeHolder
import com.mitteloupe.whoami.home.presentation.model.HomePresentationNotification
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsPresentationMapper
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsUiMapper
import com.mitteloupe.whoami.home.ui.mapper.ErrorUiMapper
import com.mitteloupe.whoami.home.ui.mapper.HomeViewStateUiMapper

data class HomeDependencies(
    val homeViewModel: HomeViewModel,
    val homeViewStateUiMapper: HomeViewStateUiMapper,
    val connectionDetailsPresentationMapper: ConnectionDetailsPresentationMapper,
    val connectionDetailsUiMapper: ConnectionDetailsUiMapper,
    private val homeNavigationMapper:
    NavigationEventDestinationMapper<HomePresentationNavigationEvent>,
    private val homeNotificationMapper: NotificationUiMapper<HomePresentationNotification>,
    val errorUiMapper: ErrorUiMapper,
    val analytics: Analytics
) : BaseComposeHolder<HomeViewState, HomePresentationNotification>(
    homeViewModel,
    homeNavigationMapper,
    homeNotificationMapper
)
