package com.mitteloupe.whoami.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.content.ConnectedContent
import com.mitteloupe.whoami.home.ui.content.DisconnectedContent
import com.mitteloupe.whoami.home.ui.content.ErrorContent
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsToUiMapper
import com.mitteloupe.whoami.widget.LoadingAnimation

@Composable
fun Home(
    homeViewModel: HomeViewModel,
    connectionDetailsToUiMapper: ConnectionDetailsToUiMapper,
    coroutineContextProvider: CoroutineContextProvider,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background
) {
    var entered by remember { mutableStateOf(false) }
    val viewState =
        homeViewModel.viewState.collectAsState(HomeViewState.Loading, coroutineContextProvider.main)

    LaunchedEffect(entered) {
        if (!entered) {
            entered = true
            homeViewModel.onEnter()
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
                modifier = Modifier.padding(12.dp, 48.dp, 12.dp, 0.dp)
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
                        connectionDetailsToUiMapper.toUi(viewStateValue),
                        Modifier.fillMaxWidth()
                    )
                }

                is HomeViewState.Disconnected -> {
                    DisconnectedContent(
                        Modifier.fillMaxWidth()
                    )
                }

                is HomeViewState.Error -> {
                    ErrorContent(
                        Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
