package com.mitteloupe.whoami.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.analytics.event.Click
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.DestinationToUiMapper
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.presentation.model.HomeNotification.ConnectionSaved
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.content.ConnectedContent
import com.mitteloupe.whoami.home.ui.content.DisconnectedContent
import com.mitteloupe.whoami.home.ui.content.ErrorContent
import com.mitteloupe.whoami.home.ui.content.NavigationButton
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsToUiMapper
import com.mitteloupe.whoami.widget.LoadingAnimation

@Composable
fun Home(
    homeViewModel: HomeViewModel,
    connectionDetailsToUiMapper: ConnectionDetailsToUiMapper,
    navigationMapper: DestinationToUiMapper,
    coroutineContextProvider: CoroutineContextProvider,
    analytics: Analytics,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background
) {
    var entered by remember { mutableStateOf(false) }
    val notification = homeViewModel.notification.collectAsState(
        initial = null,
        coroutineContextProvider.main
    )

    val navigation = homeViewModel.destination.collectAsState(
        initial = null,
        coroutineContextProvider.main
    )

    LaunchedEffect(entered) {
        if (!entered) {
            entered = true
            analytics.logScreen("Home")
            homeViewModel.onEnter()
        }
    }

    when (val notificationValue = notification.value) {
        null -> Unit

        is ConnectionSaved -> NotificationToast(notification, notificationValue)
    }

    navigation.value?.apply {
        val uiDestination = navigationMapper.toUi(this)
        LaunchedEffect(navigation) {
            uiDestination.navigate()
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = color
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 32.sp,
                modifier = Modifier.padding(10.dp, 48.dp, 10.dp, 0.dp)
            )

            val viewState = homeViewModel.viewState.collectAsState(
                HomeViewState.Loading,
                coroutineContextProvider.main
            )

            val lastConnectedState = remember { mutableStateOf<HomeViewState.Connected?>(null) }
            val viewStateValue = viewState.value

            if (viewStateValue is HomeViewState.Connected) {
                lastConnectedState.value = viewStateValue
            }

            LoadingAnimationContainer(viewStateValue)

            ConnectedContentContainer(
                viewStateValue,
                lastConnectedState,
                connectionDetailsToUiMapper
            )

            DisconnectedContentContainer(viewStateValue)

            ErrorContentContainer(viewStateValue)

            Row(
                modifier = Modifier
                    .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 24.dp)
                    .fillMaxWidth()
            ) {
                NavigationButton(
                    iconResourceId = R.drawable.icon_save,
                    label = stringResource(R.string.home_save_details_button_label),
                    onClick = {
                        val viewStateValue = viewState.value
                        if (viewStateValue is HomeViewState.Connected) {
                            analytics.logEvent(
                                Click("Save Details", mapOf("State" to "Connected"))
                            )
                            homeViewModel.onSaveDetails(viewStateValue)
                        } else {
                            analytics.logEvent(
                                Click("Save Details", mapOf("State" to "Not connected"))
                            )
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                NavigationButton(
                    iconResourceId = R.drawable.icon_history,
                    label = stringResource(R.string.home_history_button_label),
                    onClick = {
                        analytics.logEvent(Click("View History"))
                        homeViewModel.onViewHistoryAction()
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.LoadingAnimationContainer(
    viewStateValue: HomeViewState,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = viewStateValue is HomeViewState.Loading,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            LoadingAnimation()
        }
    }
}

@Stable
private fun <T> enterSpring(visibilityThreshold: T) = spring(
    dampingRatio = Spring.DampingRatioLowBouncy,
    stiffness = Spring.StiffnessMediumLow,
    visibilityThreshold = visibilityThreshold
)

@Composable
private fun ColumnScope.ConnectedContentContainer(
    viewStateValue: HomeViewState,
    lastConnectedState: MutableState<HomeViewState.Connected?>,
    connectionDetailsToUiMapper: ConnectionDetailsToUiMapper
) {
    AnimatedVisibility(
        visible = viewStateValue is HomeViewState.Connected,
        enter = expandVertically(
            animationSpec = enterSpring(IntSize.VisibilityThreshold)
        ) + slideInVertically(
            animationSpec = enterSpring(IntOffset.VisibilityThreshold)
        ) + fadeIn(),
        exit = shrinkVertically() + slideOutVertically() + fadeOut()
    ) {
        val connectedState = requireNotNull(lastConnectedState.value)
        ConnectedContent(
            connectionDetails = connectionDetailsToUiMapper.toUi(connectedState),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ColumnScope.DisconnectedContentContainer(viewStateValue: HomeViewState) {
    AnimatedVisibility(
        visible = viewStateValue is HomeViewState.Disconnected,
        enter = expandVertically(
            animationSpec = enterSpring(IntSize.VisibilityThreshold)
        ) + slideInVertically(
            animationSpec = enterSpring(IntOffset.VisibilityThreshold)
        )
    ) {
        DisconnectedContent(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun ColumnScope.ErrorContentContainer(viewStateValue: HomeViewState) {
    AnimatedVisibility(
        visible = viewStateValue is HomeViewState.Error,
        enter = expandVertically(
            animationSpec = enterSpring(IntSize.VisibilityThreshold)
        ) + slideInVertically(
            animationSpec = enterSpring(IntOffset.VisibilityThreshold)
        )
    ) {
        ErrorContent(modifier = Modifier.fillMaxWidth())
    }
}
