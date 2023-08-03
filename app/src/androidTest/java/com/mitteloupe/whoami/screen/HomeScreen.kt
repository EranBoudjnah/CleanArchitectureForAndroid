package com.mitteloupe.whoami.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
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
        assertIsDisplayed(ipAddressLabel)
    }

    fun ComposeContentTestRule.seesIpAddressSubtitleLabel() {
        assertIsDisplayed(ipAddressSubtitleLabel)
    }

    fun ComposeContentTestRule.seesCityLabel() {
        assertIsDisplayed(cityLabel)
    }

    fun ComposeContentTestRule.seesRegionLabel() {
        assertIsDisplayed(regionLabel)
    }

    fun ComposeContentTestRule.seesCountryLabel() {
        assertIsDisplayed(countryLabel)
    }

    fun ComposeContentTestRule.seesGeolocationLabel() {
        assertIsDisplayed(geolocationLabel)
    }

    fun ComposeContentTestRule.seesPostCodeLabel() {
        assertIsDisplayed(postCodeLabel)
    }

    fun ComposeContentTestRule.seesTimeZoneLabel() {
        assertIsDisplayed(timeZoneLabel)
    }

    fun ComposeContentTestRule.seesInternetServiceProviderLabel() {
        assertIsDisplayed(internetServiceProviderLabel)
    }

    private fun ComposeContentTestRule.assertIsDisplayed(
        matcher: SemanticsMatcher
    ) : SemanticsNodeInteraction {
        waitUntilAtLeastOneExists(matcher, timeoutMillis = 5_000L)
        return onNode(matcher).assertIsDisplayed()
    }
}
