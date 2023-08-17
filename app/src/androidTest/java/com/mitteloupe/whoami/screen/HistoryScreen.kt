package com.mitteloupe.whoami.screen

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.mitteloupe.whoami.test.assertion.matchesItemAtPosition

class HistoryScreen {
    private val recordsList = isAssignableFrom(RecyclerView::class.java)
    private val noRecordsLabel = withText("No saved addresses!")

    fun seeNoRecordsLabel() {
        onView(noRecordsLabel).check(matches(isDisplayed()))
    }

    fun seeIpRecord(ipAddress: String, position: Int) {
        onView(recordsList).check(
            matchesItemAtPosition(hasDescendant(withText(ipAddress)), position.zeroBased)
        )
    }

    fun seeLocation(city: String, postCode: String, position: Int) {
        onView(recordsList).check(
            matchesItemAtPosition(hasDescendant(withText("$city, $postCode")), position.zeroBased)
        )
    }

    private val Int.zeroBased
        get() = this - 1
}
