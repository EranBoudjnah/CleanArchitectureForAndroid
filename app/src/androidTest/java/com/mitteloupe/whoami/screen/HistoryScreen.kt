package com.mitteloupe.whoami.screen

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.mitteloupe.whoami.R
import com.mitteloupe.whoami.test.action.clickChildView
import com.mitteloupe.whoami.test.assertion.matchesItemAtPosition
import com.mitteloupe.whoami.test.matcher.withDrawableId

class HistoryScreen {
    private val recordsList = isAssignableFrom(RecyclerView::class.java)
    private val noRecordsLabel = withText("No saved addresses!")
    private val deleteButton = withDrawableId(R.drawable.test_icon_delete)

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

    fun tapDeleteForRecord(position: Int) {
        onView(recordsList)
            .perform(scrollToPosition<ViewHolder>(position.zeroBased))
            .perform(
                actionOnItemAtPosition<ViewHolder>(position.zeroBased, clickChildView(deleteButton))
            )
    }

    private val Int.zeroBased
        get() = this - 1
}
