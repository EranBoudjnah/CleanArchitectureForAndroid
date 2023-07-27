package com.mitteloupe.whoami.ui.navigation.mapper

import androidx.navigation.NavController
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationDestination
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationDestination.Back
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.DestinationToUiMapper
import com.mitteloupe.whoami.architecture.ui.navigation.model.UiDestination

class HistoryDestinationToUiMapper(
    private val navControllerProvider: () -> NavController
) : DestinationToUiMapper {
    override fun toUi(presentationDestination: PresentationDestination): UiDestination =
        when (presentationDestination) {
            is Back -> BackUiDestination(navControllerProvider())

            else -> error("Unknown destination: $presentationDestination")
        }

    private data class BackUiDestination(
        private val navController: NavController
    ) : UiDestination {
        override fun navigate() {
            navController.popBackStack()
        }
    }
}
