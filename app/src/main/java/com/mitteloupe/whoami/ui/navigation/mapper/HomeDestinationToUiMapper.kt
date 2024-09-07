package com.mitteloupe.whoami.ui.navigation.mapper

import android.content.Context
import android.content.Intent
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.ui.navigation.exception.UnhandledDestinationException
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.DestinationToUiMapper
import com.mitteloupe.whoami.architecture.ui.navigation.model.UiDestination
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnSaveDetails
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewHistory
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewOpenSourceNotices
import com.mitteloupe.whoami.ui.main.route.History

class HomeDestinationToUiMapper(
    private val analytics: Analytics,
    private val activityContext: Context,
    private val ossLicensesMenuIntentProvider: Context.(Class<out Any>) -> Intent =
        { javaClass -> Intent(this, javaClass) }
) : DestinationToUiMapper {
    override fun toUi(presentationNavigationEvent: PresentationNavigationEvent): UiDestination =
        when (presentationNavigationEvent) {
            is HomePresentationNavigationEvent -> presentationNavigationEvent.toUiDestination()
            else -> throw UnhandledDestinationException(presentationNavigationEvent)
        }

    private fun HomePresentationNavigationEvent.toUiDestination(): UiDestination = when (this) {
        is OnSaveDetails -> history(highlightedIpAddress)
        OnViewHistory -> history(null)
        OnViewOpenSourceNotices -> openSourceNotices()
    }

    private fun history(highlightedIpAddress: String?): UiDestination =
        UiDestination { navController -> navController.navigate(History(highlightedIpAddress)) }

    private fun openSourceNotices(): UiDestination = UiDestination {
        analytics.logScreen("Open Source Licenses")
        activityContext.startActivity(
            activityContext.ossLicensesMenuIntentProvider(OssLicensesMenuActivity::class.java)
        )
    }
}
