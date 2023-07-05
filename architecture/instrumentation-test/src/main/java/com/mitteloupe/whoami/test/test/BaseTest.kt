package com.mitteloupe.whoami.test.test

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.IdlingResource as ComposeIdlingResource
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource as EspressoIdlingResource
import androidx.test.platform.app.InstrumentationRegistry
import com.mitteloupe.whoami.test.rule.DisableAnimationsRule
import com.mitteloupe.whoami.test.rule.HiltInjectorRule
import com.mitteloupe.whoami.test.rule.ScreenshotFailureRule
import com.mitteloupe.whoami.test.rule.WebServerRule
import com.mitteloupe.whoami.test.server.MockDispatcher
import com.mitteloupe.whoami.test.server.MockWebServerProvider
import com.mitteloupe.whoami.test.server.ResponseStore
import dagger.hilt.android.testing.HiltAndroidRule
import javax.inject.Inject
import org.junit.Before
import org.junit.Rule
import org.junit.rules.RuleChain

abstract class BaseTest {
    internal val targetContext
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    internal open val hiltAndroidRule by lazy { HiltAndroidRule(this) }

    @Inject
    lateinit var mockDispatcher: MockDispatcher

    @Inject
    lateinit var mockWebServerProvider: MockWebServerProvider

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var responseStore: ResponseStore

    @Inject
    lateinit var espressoIdlingResources: @JvmSuppressWildcards Collection<EspressoIdlingResource>

    @Inject
    lateinit var composeIdlingResources: @JvmSuppressWildcards Collection<ComposeIdlingResource>

    private val webServerRule by lazy {
        WebServerRule(
            lazy { listOf(mockDispatcher) },
            lazy { responseStore }
        ) { mockWebServerProvider.serverUrl }
    }

    @get:Rule
    open val testRules: RuleChain by lazy {
        RuleChain
            .outerRule(hiltAndroidRule)
            .around(DisableAnimationsRule())
            .around(HiltInjectorRule(hiltAndroidRule))
            .around(ScreenshotFailureRule())
            .around(webServerRule)
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    abstract val startActivityLauncher: AppLauncher

    @Before
    @CallSuper
    open fun setUp() {
        registerIdlingResources()
        startActivityLauncher.launch()
    }

    private fun registerIdlingResources() {
        val idlingRegistry = IdlingRegistry.getInstance()
        idlingRegistry.register(*(espressoIdlingResources).toTypedArray())
        composeIdlingResources.forEach(composeTestRule::registerIdlingResource)
    }

    sealed class AppLauncher {
        abstract fun launch()

        data class FromIntent(private val intent: Intent) : AppLauncher() {
            override fun launch() {
                ActivityScenario.launch<Activity>(intent)
            }
        }

        data class FromClass<ACTIVITY : Activity>(
            private val activityClass: Class<out ACTIVITY>
        ) : AppLauncher() {
            @Suppress("UNCHECKED_CAST")
            override fun launch() {
                ActivityScenario.launch(activityClass) as ActivityScenario<Activity>
            }
        }

        data class FromComposable(
            private val composeContentTestRule: ComposeContentTestRule,
            private val composable: @Composable () -> Unit
        ) : AppLauncher() {
            override fun launch() {
                composeContentTestRule.setContent(composable)
            }
        }
    }
}
