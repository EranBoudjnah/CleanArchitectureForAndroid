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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.analytics.AnalyticsEvent
import com.mitteloupe.whoami.architecture.ui.view.ScreenEnterObserver
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.ui.R
import com.mitteloupe.whoami.home.ui.di.HomeDependencies
import com.mitteloupe.whoami.home.ui.model.ConnectionDetailsUiModel
import com.mitteloupe.whoami.home.ui.model.HomeViewStateUiModel
import com.mitteloupe.whoami.home.ui.model.IconLabelUiModel
import com.mitteloupe.whoami.home.ui.view.widget.ConnectedContentContainer
import com.mitteloupe.whoami.home.ui.view.widget.DisconnectedContentContainer
import com.mitteloupe.whoami.home.ui.view.widget.ErrorContentContainer
import com.mitteloupe.whoami.home.ui.view.widget.HomeFooter
import com.mitteloupe.whoami.home.ui.view.widget.LoadingAnimationContainer

@Composable
fun HomeDependencies.Home(
    navController: NavController,
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

    ViewModelObserver(navController)

    val viewState = homeViewModel.viewState.collectAsState(
        HomeViewState.Loading,
        coroutineContextProvider.main
    )

    val lastConnectedState = remember { mutableStateOf<HomeViewState.Connected?>(null) }
    val viewStateValue = viewState.value

    if (viewStateValue is HomeViewState.Connected) {
        lastConnectedState.value = viewStateValue
    }

    val connectedState = lastConnectedState.value

    val connectionDetails = remember(connectedState) {
        mutableStateOf(connectedState?.let(connectionDetailsUiMapper::toUi))
    }
    val errorMessage = remember(viewStateValue) {
        mutableStateOf(
            (viewStateValue as? HomeViewState.Error)?.let(errorUiMapper::toUi).orEmpty()
        )
    }
    HomeContents(
        viewState = homeViewStateUiMapper.toUi(viewStateValue),
        connectionDetails = connectionDetails.value,
        errorMessage = errorMessage.value,
        analytics = analytics,
        onSaveDetailsClick = { relaySavingToViewModel(viewState) },
        onViewHistoryClick = { homeViewModel.onViewHistoryAction() },
        onOpenSourceNoticesClick = { homeViewModel.onOpenSourceNoticesAction() },
        color = color
    )
}

@Composable
private fun HomeContents(
    viewState: HomeViewStateUiModel,
    connectionDetails: ConnectionDetailsUiModel?,
    errorMessage: String,
    analytics: Analytics,
    onSaveDetailsClick: () -> Unit,
    onViewHistoryClick: () -> Unit,
    onOpenSourceNoticesClick: () -> Unit,
    color: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(10.dp, 48.dp, 10.dp, 0.dp)
        )

        LoadingAnimationContainer(visible = viewState is HomeViewStateUiModel.Loading)

        ConnectedContentContainer(
            visible = viewState is HomeViewStateUiModel.Connected,
            connectionDetails = connectionDetails
        )

        DisconnectedContentContainer(
            visible = viewState is HomeViewStateUiModel.Disconnected
        )

        ErrorContentContainer(
            visible = viewState is HomeViewStateUiModel.Error,
            errorText = errorMessage
        )

        HomeFooter(
            connected = viewState is HomeViewStateUiModel.Connected,
            analytics = analytics,
            onSaveDetailsClick = onSaveDetailsClick,
            onViewHistoryClick = onViewHistoryClick,
            onOpenSourceNoticesClick = onOpenSourceNoticesClick
        )
    }
}

@Preview
@Composable
private fun PreviewLoading() {
    HomeContents(
        viewState = HomeViewStateUiModel.Loading,
        connectionDetails = null,
        errorMessage = "",
        analytics = object : Analytics {
            override fun logScreen(screenName: String) = Unit
            override fun logEvent(event: AnalyticsEvent) = Unit
        },
        onSaveDetailsClick = {},
        onViewHistoryClick = {},
        onOpenSourceNoticesClick = {},
        color = MaterialTheme.colorScheme.background
    )
}

@Preview
@Composable
private fun PreviewDisconnected() {
    HomeContents(
        viewState = HomeViewStateUiModel.Disconnected,
        connectionDetails = null,
        errorMessage = "",
        analytics = object : Analytics {
            override fun logScreen(screenName: String) = Unit
            override fun logEvent(event: AnalyticsEvent) = Unit
        },
        onSaveDetailsClick = {},
        onViewHistoryClick = {},
        onOpenSourceNoticesClick = {},
        color = MaterialTheme.colorScheme.background
    )
}

@Preview
@Composable
private fun PreviewConnected() {
    HomeContents(
        viewState = HomeViewStateUiModel.Connected,
        connectionDetails = ConnectionDetailsUiModel(
            ipAddress = "255.255.255.255",
            cityIconLabel = IconLabelUiModel(R.drawable.icon_city, "Haifa"),
            regionIconLabel = IconLabelUiModel(R.drawable.icon_region, "North"),
            countryIconLabel = IconLabelUiModel(R.drawable.icon_country, "Israel"),
            geolocationIconLabel = IconLabelUiModel(R.drawable.icon_geolocation, "0.0, 0.0"),
            postCode = IconLabelUiModel(R.drawable.icon_post_code, "12345"),
            timeZone = IconLabelUiModel(R.drawable.icon_time_zone, "GMT +0200"),
            internetServiceProviderName = IconLabelUiModel(
                R.drawable.icon_internet_service_provider,
                "Fast Connection Limited"
            )
        ),
        errorMessage = "",
        analytics = object : Analytics {
            override fun logScreen(screenName: String) = Unit
            override fun logEvent(event: AnalyticsEvent) = Unit
        },
        onSaveDetailsClick = {},
        onViewHistoryClick = {},
        onOpenSourceNoticesClick = {},
        color = MaterialTheme.colorScheme.background
    )
}

@Preview
@Composable
private fun PreviewError() {
    HomeContents(
        viewState = HomeViewStateUiModel.Error,
        connectionDetails = null,
        errorMessage = "",
        analytics = object : Analytics {
            override fun logScreen(screenName: String) = Unit
            override fun logEvent(event: AnalyticsEvent) = Unit
        },
        onSaveDetailsClick = {},
        onViewHistoryClick = {},
        onOpenSourceNoticesClick = {},
        color = MaterialTheme.colorScheme.background
    )
}
