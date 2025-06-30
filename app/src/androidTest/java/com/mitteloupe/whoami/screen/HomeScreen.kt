package com.mitteloupe.whoami.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.click
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.performTouchInput
import com.mitteloupe.whoami.constant.IP_ADDRESS

@ExperimentalTestApi
class HomeScreen {
    private val ipAddressLabel = hasText(IP_ADDRESS)
    private val ipAddressSubtitleLabel =
        hasText("This is the address from which you are sending network requests.")
    private val cityLabel = hasText("Brentwood")
    private val regionLabel = hasText("England")
    private val countryLabel = hasText("United Kingdom")
    private val geolocationLabel = hasText("51.6213, 0.3056")
    private val postCodeLabel = hasText("CM14")
    private val timeZoneLabel = hasText("Europe/London")
    private val internetServiceProviderLabel = hasText("TalkTalk Limited")
    private val saveDetailsButton = hasText("Save Details")
    private val openSourceNoticesButton = hasText("Open Source Notices")

    fun ComposeContentTestRule.seeIpAddressLabel() {
        assertIsDisplayed(ipAddressLabel)
    }

    fun ComposeContentTestRule.seeIpAddressSubtitleLabel() {
        assertIsDisplayed(ipAddressSubtitleLabel)
    }

    fun ComposeContentTestRule.seeCityLabel() {
        assertIsDisplayed(cityLabel)
    }

    fun ComposeContentTestRule.seeRegionLabel() {
        assertIsDisplayed(regionLabel)
    }

    fun ComposeContentTestRule.seeCountryLabel() {
        assertIsDisplayed(countryLabel)
    }

    fun ComposeContentTestRule.seeGeolocationLabel() {
        assertIsDisplayed(geolocationLabel)
    }

    fun ComposeContentTestRule.seePostCodeLabel() {
        assertIsDisplayed(postCodeLabel)
    }

    fun ComposeContentTestRule.seeTimeZoneLabel() {
        assertIsDisplayed(timeZoneLabel)
    }

    fun ComposeContentTestRule.seeInternetServiceProviderLabel() {
        assertIsDisplayed(internetServiceProviderLabel)
    }

    fun ComposeContentTestRule.tapSaveDetailsButton() {
        onNode(saveDetailsButton).performTouchInput { click() }
    }

    fun ComposeContentTestRule.tapOpenSourceNoticesButton() {
        onNode(openSourceNoticesButton).performTouchInput { click() }
    }

    private fun ComposeContentTestRule.assertIsDisplayed(matcher: SemanticsMatcher) {
        waitUntilExactlyOneExists(matcher, timeoutMillis = 15_000L)
    }
}
