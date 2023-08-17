package com.mitteloupe.whoami.test.assertion

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import org.hamcrest.Matcher

fun matchesItemAtPosition(matcher: Matcher<View?>?, position: Int) =
    ViewAssertion { view, noViewFoundException ->
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as RecyclerView
        val viewHolder = requireNotNull(recyclerView.findViewHolderForAdapterPosition(position)) {
            "No view holder at position: $position"
        }
        assertThat(viewHolder.itemView, matcher)
    }
