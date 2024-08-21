package com.mitteloupe.whoami.test.matcher

import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun withBackgroundColorMatcher(
    @ColorInt color: Int,
    matchCardViewBackgrounds: Boolean
): Matcher<View> = WithBackgroundColorMatcher(color, matchCardViewBackgrounds)

class WithBackgroundColorMatcher(
    @ColorInt private val expectedColor: Int,
    private val matchCardViewBackgrounds: Boolean = false
) : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description?) {
        @OptIn(ExperimentalStdlibApi::class)
        description?.appendText("has background color: #${expectedColor.toHexString()}")
    }

    override fun matchesSafely(item: View): Boolean {
        val textViewColor = if (matchCardViewBackgrounds) {
            (item as? CardView)?.cardBackgroundColor?.getColorForState(item.drawableState, -1)
                .also {
                    @OptIn(ExperimentalStdlibApi::class)
                    println("Background color: #${it?.toHexString()}")
                }
        } else {
            (item.background as? ColorDrawable)?.color
        }

        return textViewColor == expectedColor
    }
}
