package com.mitteloupe.whoami.home.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mitteloupe.whoami.architecture.ui.view.ScreenEnterObserver
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.ui.R
import com.mitteloupe.whoami.home.ui.di.HomeDependencies
import com.mitteloupe.whoami.home.ui.view.widget.ConnectedContentContainer
import com.mitteloupe.whoami.home.ui.view.widget.DisconnectedContentContainer
import com.mitteloupe.whoami.home.ui.view.widget.ErrorContentContainer
import com.mitteloupe.whoami.home.ui.view.widget.HomeFooter
import com.mitteloupe.whoami.home.ui.view.widget.LoadingAnimationContainer

@Composable
fun HomeDependencies.Home(
    color: Color = MaterialTheme.colorScheme.background
) {
    fun relaySavingToViewModel(viewState: State<HomeViewState>) {
        val connectionDetails = viewState.value
        require(connectionDetails is HomeViewState.Connected) {
            "Unexpected click, not connected."
        }
        homeViewModel.onSaveDetailsAction(connectionDetails)
    }

    ScreenEnterObserver {
        analytics.logScreen("Home")
        homeViewModel.onEnter()
    }

    ViewModelObserver()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
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

        LoadingAnimationContainer(visible = viewStateValue is HomeViewState.Loading)

        val connectedState = lastConnectedState.value
        ConnectedContentContainer(
            visible = viewStateValue is HomeViewState.Connected,
            connectionDetails = connectedState?.let(connectionDetailsToUiMapper::toUi)
        )

        DisconnectedContentContainer(visible = viewStateValue is HomeViewState.Disconnected)

        ErrorContentContainer(
            visible = viewStateValue is HomeViewState.Error,
            errorText = (viewStateValue as? HomeViewState.Error)?.let(errorToUiMapper::toUi)
                .orEmpty()
        )

        HomeFooter(
            connected = viewState.value is HomeViewState.Connected,
            analytics = analytics,
            onSaveDetailsClick = { relaySavingToViewModel(viewState) },
            onViewHistoryClick = { homeViewModel.onViewHistoryAction() },
            onOpenSourceNoticesClick = { homeViewModel.onOpenSourceNoticesAction() }
        )
    }
}
