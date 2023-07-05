package com.mitteloupe.whoami.test.rule

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import java.io.IOException
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class DisableAnimationsRule : TestRule {
    private var transitionAnimationScale: Float = 0f
    private var windowAnimationScale: Float = 0f
    private var animatprDurationScale: Float = 0f

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                disableAnimations()
                try {
                    base.evaluate()
                } finally {
                    enableAnimations()
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun disableAnimations() {
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).apply {
            transitionAnimationScale =
                executeShellCommand("settings get global transition_animation_scale")
                    .orDefault().toFloat()
            windowAnimationScale =
                executeShellCommand("settings get global window_animation_scale")
                    .orDefault().toFloat()
            animatprDurationScale =
                executeShellCommand("settings get global animator_duration_scale")
                    .orDefault().toFloat()
            executeShellCommand("settings put global transition_animation_scale 0")
            executeShellCommand("settings put global window_animation_scale 0")
            executeShellCommand("settings put global animator_duration_scale 0")
        }
    }

    @Throws(IOException::class)
    private fun enableAnimations() {
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).apply {
            executeShellCommand(
                "settings put global transition_animation_scale $transitionAnimationScale"
            )
            executeShellCommand("settings put global window_animation_scale $windowAnimationScale")
            executeShellCommand(
                "settings put global animator_duration_scale $animatprDurationScale"
            )
        }
    }

    private fun String.orDefault() = if (isEmpty() || trim() == "null") {
        "1"
    } else {
        this
    }
}
