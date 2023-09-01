package com.mitteloupe.whoami.architecture.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationDestination
import com.mitteloupe.whoami.architecture.presentation.notification.PresentationNotification
import com.mitteloupe.whoami.architecture.presentation.viewmodel.BaseViewModel
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.DestinationToUiMapper
import com.mitteloupe.whoami.architecture.ui.notification.mapper.NotificationToUiMapper
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider

abstract class BaseComposeHolder<VIEW_STATE : Any, NOTIFICATION : PresentationNotification>(
    private val viewModel: BaseViewModel<VIEW_STATE, NOTIFICATION>,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val navigationMapper: DestinationToUiMapper,
    private val notificationMapper: NotificationToUiMapper
) {

    @Composable
    fun ViewModelObserver() {
        val navigation = viewModel.destination.collectAsState(
            initial = null,
            coroutineContextProvider.main
        )

        navigation.value?.let { navigationValue ->
            Navigate(navigationValue)
        }

        val notification = viewModel.notification.collectAsState(
            initial = null,
            coroutineContextProvider.main
        )

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
    fun Navigate(navigation: PresentationDestination) {
        navigationMapper.toUi(navigation).let { uiDestination ->
            LaunchedEffect(navigation) {
                uiDestination.navigate()
            }
        }
    }
}
