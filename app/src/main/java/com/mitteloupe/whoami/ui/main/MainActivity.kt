package com.mitteloupe.whoami.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsToUiMapper
import com.mitteloupe.whoami.home.ui.mapper.ErrorToUiMapper
import com.mitteloupe.whoami.home.ui.mapper.HomeNotificationToUiMapper
import com.mitteloupe.whoami.ui.main.di.AppNavHostDependencies
import com.mitteloupe.whoami.ui.navigation.mapper.HomeDestinationToUiMapper
import com.mitteloupe.whoami.ui.theme.WhoAmITheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var homeDestinationToUiMapper: HomeDestinationToUiMapper

    @Inject
    lateinit var homeNotificationToUiMapper: HomeNotificationToUiMapper

    @Inject
    lateinit var errorToUiMapper: ErrorToUiMapper

    @Inject
    lateinit var coroutineContextProvider: CoroutineContextProvider

    @Inject
    lateinit var connectionDetailsToUiMapper: ConnectionDetailsToUiMapper

    @Inject
    lateinit var analytics: Analytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhoAmITheme {
                AppNavHostDependencies(
                    homeViewModel,
                    homeDestinationToUiMapper,
                    homeNotificationToUiMapper,
                    errorToUiMapper,
                    connectionDetailsToUiMapper,
                    coroutineContextProvider,
                    analytics
                ).AppNavHost(supportFragmentManager)
            }
        }
    }
}
