package com.mitteloupe.whoami.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.content.ConnectedContent
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsToUiMapper
import com.mitteloupe.whoami.home.ui.model.ConnectionDetailsUiModel
import com.mitteloupe.whoami.ui.main.model.AppNavHostDependencies
import com.mitteloupe.whoami.ui.theme.WhoAmITheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var coroutineContextProvider: CoroutineContextProvider

    @Inject
    lateinit var connectionDetailsToUiMapper: ConnectionDetailsToUiMapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhoAmITheme {
                AppNavHost(
                    AppNavHostDependencies(
                        homeViewModel,
                        connectionDetailsToUiMapper,
                        coroutineContextProvider
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WhoAmITheme {
        ConnectedContent(
            ConnectionDetailsUiModel(
                ipAddress = "0.0.0.0",
                city = "Brentwood",
                region = "England",
                countryName = "GB",
                geolocation = "0, 0",
                postCode = "AB12 3CD",
                timeZone = "Europe/London",
                internetServiceProviderName = "TalkTalk"
            )
        )
    }
}
