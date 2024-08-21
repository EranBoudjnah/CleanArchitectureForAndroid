package com.mitteloupe.whoami.architecture.ui.navigation.exception

import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationDestination

class UnhandledDestinationException(destination: PresentationDestination) :
    IllegalArgumentException(
        "Navigation to ${destination::class.simpleName} was not handled."
    )
