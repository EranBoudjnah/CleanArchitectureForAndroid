package com.mitteloupe.whoami.screen

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule

class OpenSourceNoticesScreen : ComposeScreen() {
    private val titleLabel =
        hasText("Open Source Licenses")

    fun ComposeContentTestRule.seeScreen() {
        assertIsDisplayed(titleLabel)
    }
}
