package com.mitteloupe.whoami.screen

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText

class HistoryScreen {
    private val noRecordsLabel = withText("No saved addresses!")

    fun seeNoRecordsLabel() {
        onView(noRecordsLabel).check(matches(isDisplayed()))
    }

    fun seeIpRecord(ipAddress: String) {
        onView(withText(ipAddress)).check(matches(isDisplayed()))
    }
}
