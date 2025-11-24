package com.mitteloupe.whoami.navigation.mapper

import com.mitteloupe.whoami.architecture.ui.navigation.mapper.NavigationEventDestinationMapper
import com.mitteloupe.whoami.architecture.ui.navigation.model.UiDestination
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnSavedDetails
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewHistory
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewOpenSourceNotices
import com.mitteloupe.whoami.ui.main.route.History
import com.mitteloupe.whoami.ui.main.route.OpenSourceNotices

class HomeNavigationEventDestinationMapper :
    NavigationEventDestinationMapper<HomePresentationNavigationEvent>(
        HomePresentationNavigationEvent::class
    ) {
    override fun mapTypedEvent(navigationEvent: HomePresentationNavigationEvent): UiDestination =
        when (navigationEvent) {
            is OnSavedDetails -> history(navigationEvent.savedIpAddress)
            OnViewHistory -> history(null)
            OnViewOpenSourceNotices -> openSourceNotices()
        }

    private fun history(highlightedIpAddress: String?): UiDestination =
        UiDestination { backStack -> backStack.add(History(highlightedIpAddress)) }

    private fun openSourceNotices(): UiDestination = UiDestination { backStack ->
        backStack.add(OpenSourceNotices)
    }
}
