package com.mitteloupe.whoami.ui.navigation.mapper

import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent.Back
import com.mitteloupe.whoami.architecture.ui.navigation.exception.UnhandledDestinationException
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.NavigationEventToDestinationMapper
import com.mitteloupe.whoami.architecture.ui.navigation.model.UiDestination

class HistoryNavigationEventToDestinationMapper :
    NavigationEventToDestinationMapper<PresentationNavigationEvent>(
        PresentationNavigationEvent::class
    ) {
    override fun mapTypedEvent(navigationEvent: PresentationNavigationEvent): UiDestination =
        when (navigationEvent) {
            is Back -> backUiDestination()
            else -> throw UnhandledDestinationException(navigationEvent)
        }

    private fun backUiDestination() = UiDestination { navController ->
        navController.navigateUp()
    }
}
