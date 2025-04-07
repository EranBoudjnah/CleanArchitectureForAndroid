package com.mitteloupe.whoami.test.rule

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import java.io.IOException
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

private val animationKeys = setOf(
    "transition_animation_scale",
    "window_animation_scale",
    "animator_duration_scale"
)

private const val DEFAULT_SCALE = 1f

class DisableAnimationsRule : TestRule {
    private val savedScaleValues = mutableMapOf<String, Float>()
    private val device: UiDevice
        get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    override fun apply(base: Statement, description: Description): Statement =
        object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                saveAndDisableAnimations()
                try {
                    base.evaluate()
                } finally {
                    restoreAnimations()
                }
            }
        }

    @Throws(IOException::class)
    private fun saveAndDisableAnimations() {
        animationKeys.forEach { key ->
            savedScaleValues[key] =
                device.executeShellCommand("settings get global $key").orDefault()
            device.executeShellCommand("settings put global $key 0")
        }
    }

    @Throws(IOException::class)
    private fun restoreAnimations() {
        animationKeys.forEach { key ->
            val savedValue = savedScaleValues[key] ?: DEFAULT_SCALE
            device.executeShellCommand("settings put global $key $savedValue")
        }
    }

    private fun String?.orDefault() = if (isNullOrEmpty() || trim() == "null") {
        DEFAULT_SCALE
    } else {
        toFloat()
    }
}
