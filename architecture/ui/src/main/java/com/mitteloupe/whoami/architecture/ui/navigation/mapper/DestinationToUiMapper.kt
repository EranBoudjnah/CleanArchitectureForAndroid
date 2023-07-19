package com.mitteloupe.whoami.architecture.ui.navigation.mapper

import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationDestination
import com.mitteloupe.whoami.architecture.ui.navigation.model.UiDestination

interface DestinationToUiMapper {
    fun toUi(presentationDestination: PresentationDestination): UiDestination
}
