package com.mitteloupe.whoami.navigation.mapper

import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent.Back
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsEmptyCollection.empty
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class HistoryNavigationEventDestinationMapperTest(
    private val givenNavigationEvent: PresentationNavigationEvent,
    private val assertExpectation: (MutableList<Any>) -> Unit
) {
    companion object {
        @JvmStatic
        @Parameters(name = "Given {0}")
        fun data(): Iterable<Array<*>> = setOf(
            testCase(Back) { backStack ->
                assertThat(backStack, `is`(empty()))
            }
        )

        private fun testCase(
            navigationEvent: PresentationNavigationEvent,
            assertExpectation: (MutableList<Any>) -> Unit
        ) = arrayOf(navigationEvent, assertExpectation)
    }

    private lateinit var classUnderTest: HistoryNavigationEventDestinationMapper

    @Before
    fun setUp() {
        classUnderTest = HistoryNavigationEventDestinationMapper()
    }

    @Test
    fun `When toUi then returns expected destination`() {
        // Given
        val backStack = mutableListOf<Any>()

        // When
        val destination = classUnderTest.toUi(givenNavigationEvent)
        destination.navigate(backStack)

        // Then
        assertExpectation(backStack)
    }
}
