package com.mitteloupe.whoami.test.action

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

fun clickChildView(matcher: Matcher<View>) = ClickChildView(matcher)

class ClickChildView(private val matcher: Matcher<View>) : ViewAction {
    override fun getConstraints(): Matcher<View> = allOf(isDisplayed(), matcher)

    override fun getDescription() = "Click on a matching view"

    override fun perform(uiController: UiController, view: View) {
        clickOnMatchingView(view)
    }

    private fun clickOnMatchingView(view: View): Boolean {
        if (matcher.matches(view)) {
            view.performClick()
            return true
        }

        return if (view is ViewGroup) {
            view.children.iterator().asSequence().firstOrNull { childView ->
                clickOnMatchingView(childView)
            } != null
        } else {
            false
        }
    }
}
