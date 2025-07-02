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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
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
fun HomeDependencies.Home(navController: NavController, modifier: Modifier = Modifier) {
    fun relaySavingToViewModel(connectionDetails: ConnectionDetailsUiModel) {
        val presentationConnection = connectionDetailsPresentationMapper
            .toPresentation(connectionDetails)
        homeViewModel.onSaveDetailsAction(presentationConnection)
    }

    ScreenEnterObserver {
        analytics.logScreen("Home")
        homeViewModel.onEnter()
    }

    ViewModelObserver(navController)

    val viewState by homeViewModel.viewState.collectAsState(HomeViewState.Loading)

    val connectionDetails by rememberSaveable(viewState) {
        mutableStateOf(
            (viewState as? HomeViewState.Connected)?.let(connectionDetailsUiMapper::toUi)
        )
    }
    val errorMessage by rememberSaveable(viewState) {
        mutableStateOf((viewState as? HomeViewState.Error)?.let(errorUiMapper::toUi).orEmpty())
    }
    val uiState by rememberSaveable(viewState) {
        mutableStateOf(homeViewStateUiMapper.toUi(viewState))
    }

    HomeContents(
        viewState = uiState,
        connectionDetails = connectionDetails,
        errorMessage = errorMessage,
        analytics = analytics,
        onSaveDetailsClick = {
            val latestConnectionDetails = connectionDetails
            requireNotNull(latestConnectionDetails)
            relaySavingToViewModel(latestConnectionDetails)
        },
        onViewHistoryClick = { homeViewModel.onViewHistoryAction() },
        onOpenSourceNoticesClick = { homeViewModel.onOpenSourceNoticesAction() },
        modifier = modifier
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
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
        onOpenSourceNoticesClick = {}
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
        onOpenSourceNoticesClick = {}
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
        onOpenSourceNoticesClick = {}
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
        onOpenSourceNoticesClick = {}
    )
}
