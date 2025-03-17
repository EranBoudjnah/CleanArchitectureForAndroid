package com.mitteloupe.whoami.navigation.mapper

import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent.Back
import com.mitteloupe.whoami.architecture.ui.navigation.exception.UnhandledNavigationException
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.NavigationEventDestinationMapper
import com.mitteloupe.whoami.architecture.ui.navigation.model.UiDestination

class HistoryNavigationEventDestinationMapper :
    NavigationEventDestinationMapper<PresentationNavigationEvent>(
        PresentationNavigationEvent::class
    ) {
    override fun mapTypedEvent(navigationEvent: PresentationNavigationEvent): UiDestination =
        when (navigationEvent) {
            is Back -> backUiDestination()
            else -> throw UnhandledNavigationException(navigationEvent)
        }

    private fun backUiDestination() = UiDestination { navController ->
        navController.navigateUp()
    }
}
