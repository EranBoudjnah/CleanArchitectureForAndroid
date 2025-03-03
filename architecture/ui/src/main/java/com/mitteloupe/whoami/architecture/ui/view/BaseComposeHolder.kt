package com.mitteloupe.whoami.architecture.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.presentation.notification.PresentationNotification
import com.mitteloupe.whoami.architecture.presentation.viewmodel.BaseViewModel
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.NavigationEventDestinationMapper
import com.mitteloupe.whoami.architecture.ui.notification.mapper.NotificationUiMapper

abstract class BaseComposeHolder<VIEW_STATE : Any, NOTIFICATION : PresentationNotification>(
    private val viewModel: BaseViewModel<VIEW_STATE, NOTIFICATION>,
    private val navigationMapper: NavigationEventDestinationMapper<PresentationNavigationEvent>,
    private val notificationMapper: NotificationUiMapper
) {
    @Composable
    fun ViewModelObserver(navController: NavController) {
        val navigation = viewModel.navigationEvent.collectAsState(initial = null)

        navigation.value?.let { navigationValue ->
            Navigate(navigationValue, navController)
        }

        val notification = viewModel.notification.collectAsState(initial = null)

        notification.value?.let { notificationValue ->
            Notify(notification = notificationValue)
        }
    }

    @Composable
    fun Notify(notification: PresentationNotification) {
        notificationMapper.toUi(notification).let { uiNotification ->
            LaunchedEffect(notification) {
                uiNotification.present()
            }
        }
    }

    @Composable
    fun Navigate(navigation: PresentationNavigationEvent, navController: NavController) {
        navigationMapper.toUi(navigation).let { uiDestination ->
            LaunchedEffect(navigation) {
                uiDestination.navigate(navController)
            }
        }
    }
}
