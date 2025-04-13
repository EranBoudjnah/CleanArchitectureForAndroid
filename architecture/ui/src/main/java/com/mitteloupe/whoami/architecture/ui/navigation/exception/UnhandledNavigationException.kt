package com.mitteloupe.whoami.architecture.ui.navigation.exception

import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent

class UnhandledNavigationException(event: PresentationNavigationEvent) :
    IllegalArgumentException(
        "Navigation event ${event::class.simpleName} was not handled."
    )
