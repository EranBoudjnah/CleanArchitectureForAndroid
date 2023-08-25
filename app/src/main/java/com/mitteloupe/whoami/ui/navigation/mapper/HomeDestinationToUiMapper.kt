package com.mitteloupe.whoami.ui.navigation.mapper

import android.content.Context
import android.content.Intent
import androidx.navigation.NavHostController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationDestination
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.DestinationToUiMapper
import com.mitteloupe.whoami.architecture.ui.navigation.model.UiDestination
import com.mitteloupe.whoami.home.presentation.navigation.ViewHistoryPresentationDestination
import com.mitteloupe.whoami.home.presentation.navigation.ViewOpenSourceNoticesPresentationDestination

class HomeDestinationToUiMapper(
    private val activityContext: Context,
    private val ossLicensesMenuIntentProvider: Context.(Class<out Any>) -> Intent =
        { javaClass -> Intent(this, javaClass) }
) : DestinationToUiMapper {
    private lateinit var navController: NavHostController

    override fun toUi(presentationDestination: PresentationDestination): UiDestination =
        when (presentationDestination) {
            is ViewHistoryPresentationDestination -> {
                HistoryUiDestination(navController)
            }

            is ViewOpenSourceNoticesPresentationDestination -> {
                OpenSourceNoticesUiDestination(activityContext, ossLicensesMenuIntentProvider)
            }

            else -> error("Unknown destination: $presentationDestination")
        }

    fun setNavController(navController: NavHostController) {
        this.navController = navController
    }

    private data class HistoryUiDestination(
        private val navHostController: NavHostController
    ) : UiDestination {
        override fun navigate() {
            navHostController.navigate("history")
        }
    }

    private data class OpenSourceNoticesUiDestination(
        private val context: Context,
        private val ossLicensesMenuIntentProvider: Context.(Class<out Any>) -> Intent
    ) : UiDestination {
        override fun navigate() {
            context.startActivity(
                context.ossLicensesMenuIntentProvider(OssLicensesMenuActivity::class.java)
            )
        }
    }
}
