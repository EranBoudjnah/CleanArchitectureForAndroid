package com.mitteloupe.whoami.architecture.ui.navigation.exception

import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent

class UnhandledDestinationException(destination: PresentationNavigationEvent) :
    IllegalArgumentException(
        "Navigation to ${destination::class.simpleName} was not handled."
    )
