package com.mitteloupe.whoami.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
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

    fun ComposeContentTestRule.seesIpAddressLabel() {
        waitUntilAtLeastOneExists(ipAddressLabel)
    }

    fun ComposeContentTestRule.seesIpAddressSubtitleLabel() {
        onNode(ipAddressSubtitleLabel).assertIsDisplayed()
    }

    fun ComposeContentTestRule.seesCityLabel() {
        onNode(cityLabel).assertIsDisplayed()
    }

    fun ComposeContentTestRule.seesRegionLabel() {
        onNode(regionLabel).assertIsDisplayed()
    }

    fun ComposeContentTestRule.seesCountryLabel() {
        onNode(countryLabel).assertIsDisplayed()
    }

    fun ComposeContentTestRule.seesGeolocationLabel() {
        onNode(geolocationLabel).assertIsDisplayed()
    }

    fun ComposeContentTestRule.seesPostCodeLabel() {
        onNode(postCodeLabel).assertIsDisplayed()
    }

    fun ComposeContentTestRule.seesTimeZoneLabel() {
        onNode(timeZoneLabel).assertIsDisplayed()
    }

    fun ComposeContentTestRule.seesInternetServiceProviderLabel() {
        onNode(internetServiceProviderLabel).assertIsDisplayed()
    }
}
