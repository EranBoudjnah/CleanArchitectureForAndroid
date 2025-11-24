package com.mitteloupe.whoami.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.ComposeContentTestRule

abstract class ComposeScreen {
    @OptIn(ExperimentalTestApi::class)
    protected fun ComposeContentTestRule.assertIsDisplayed(matcher: SemanticsMatcher) {
        waitUntilExactlyOneExists(matcher, timeoutMillis = 15_000L)
    }
}
