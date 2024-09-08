package com.mitteloupe.whoami.ui.navigation.mapper

import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent.Back
import com.mitteloupe.whoami.architecture.ui.navigation.exception.UnhandledDestinationException
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.DestinationToUiMapper
import com.mitteloupe.whoami.architecture.ui.navigation.model.UiDestination

class HistoryDestinationToUiMapper : DestinationToUiMapper {
    override fun toUi(presentationNavigationEvent: PresentationNavigationEvent): UiDestination =
        when (presentationNavigationEvent) {
            is Back -> backUiDestination()
            else -> throw UnhandledDestinationException(presentationNavigationEvent)
        }

    private fun backUiDestination() = UiDestination { navController ->
        navController.navigateUp()
    }
}
