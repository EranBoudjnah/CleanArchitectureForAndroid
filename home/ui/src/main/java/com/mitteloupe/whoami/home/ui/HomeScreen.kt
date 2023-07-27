package com.mitteloupe.whoami.home.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            homeViewModel.onEnter()
        }
    }

    when (val notificationValue = notification.value) {
        null -> Unit

        is ConnectionSaved -> {
            val context = LocalContext.current
            LaunchedEffect(notification) {
                Toast.makeText(
                    context,
                    context.getString(
                        R.string.home_details_saved_notification,
                        notificationValue.ipAddress
                    ),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
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

            when (val viewStateValue = viewState.value) {
                is HomeViewState.Loading -> {
                    Box(
                        modifier = modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingAnimation()
                    }
                }

                is HomeViewState.Connected -> {
                    ConnectedContent(
                        connectionDetails = connectionDetailsToUiMapper.toUi(viewStateValue),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                is HomeViewState.Disconnected -> {
                    DisconnectedContent(modifier = Modifier.fillMaxWidth())
                }

                is HomeViewState.Error -> {
                    ErrorContent(modifier = Modifier.fillMaxWidth())
                }
            }

            Row(
                modifier = Modifier
                    .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 24.dp)
                    .fillMaxWidth()
            ) {
                when (val viewStateValue = viewState.value) {
                    is HomeViewState.Connected -> {
                        NavigationButton(
                            iconResourceId = R.drawable.icon_save,
                            label = stringResource(R.string.home_save_details_button_label),
                            onClick = {
                                homeViewModel.onSaveDetails(viewStateValue)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    else -> {
                        NavigationButton(
                            iconResourceId = R.drawable.icon_save,
                            label = stringResource(R.string.home_save_details_button_label),
                            onClick = {},
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                NavigationButton(
                    iconResourceId = R.drawable.icon_history,
                    label = stringResource(R.string.home_history_button_label),
                    onClick = {
                        homeViewModel.onViewHistoryAction()
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
