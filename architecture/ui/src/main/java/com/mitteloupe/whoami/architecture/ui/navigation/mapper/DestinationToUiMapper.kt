package com.mitteloupe.whoami.architecture.ui.navigation.mapper

import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.ui.navigation.model.UiDestination

interface DestinationToUiMapper {
    fun toUi(presentationNavigationEvent: PresentationNavigationEvent): UiDestination
}
