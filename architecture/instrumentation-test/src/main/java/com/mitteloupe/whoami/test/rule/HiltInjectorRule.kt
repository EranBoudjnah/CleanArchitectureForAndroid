package com.mitteloupe.whoami.test.rule

import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class HiltInjectorRule(private val hiltAndroidRule: HiltAndroidRule) : TestRule {
    override fun apply(base: Statement, description: Description): Statement =
        object : Statement() {
            override fun evaluate() {
                hiltAndroidRule.inject()
                base.evaluate()
            }
        }
}
