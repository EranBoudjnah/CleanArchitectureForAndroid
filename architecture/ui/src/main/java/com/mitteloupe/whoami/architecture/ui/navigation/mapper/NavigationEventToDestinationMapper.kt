package com.mitteloupe.whoami.architecture.ui.navigation.mapper

import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.ui.navigation.model.UiDestination

interface NavigationEventToDestinationMapper {
    fun toUi(navigationEvent: PresentationNavigationEvent): UiDestination
}
