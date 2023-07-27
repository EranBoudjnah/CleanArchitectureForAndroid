package com.mitteloupe.whoami.ui.navigation.mapper

import androidx.navigation.NavHostController
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationDestination
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.DestinationToUiMapper
import com.mitteloupe.whoami.architecture.ui.navigation.model.UiDestination
import com.mitteloupe.whoami.home.presentation.navigation.ViewHistoryPresentationDestination

class HomeDestinationToUiMapper : DestinationToUiMapper {
    private lateinit var navController: NavHostController

    override fun toUi(presentationDestination: PresentationDestination): UiDestination =
        when (presentationDestination) {
            is ViewHistoryPresentationDestination -> {
                HistoryUiDestination(navController)
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
}
