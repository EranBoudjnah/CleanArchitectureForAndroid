package com.mitteloupe.whoami.home.presentation.navigation

import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent

sealed interface HomePresentationNavigationEvent : PresentationNavigationEvent {
    data object OnViewHistory : HomePresentationNavigationEvent

    data class OnSavedDetails(val savedIpAddress: String) : HomePresentationNavigationEvent

    data object OnViewOpenSourceNotices : HomePresentationNavigationEvent
}
