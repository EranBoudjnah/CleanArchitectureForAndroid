package com.mitteloupe.whoami.ui.navigation.mapper

import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationDestination
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationDestination.Back
import com.mitteloupe.whoami.architecture.ui.navigation.exception.UnhandledDestinationException
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.DestinationToUiMapper
import com.mitteloupe.whoami.architecture.ui.navigation.model.UiDestination

class HistoryDestinationToUiMapper : DestinationToUiMapper {
    override fun toUi(presentationDestination: PresentationDestination): UiDestination =
        when (presentationDestination) {
            is Back -> backUiDestination()
            else -> throw UnhandledDestinationException(presentationDestination)
        }

    private fun backUiDestination() = UiDestination { navController ->
        navController.navigateUp()
    }
}
