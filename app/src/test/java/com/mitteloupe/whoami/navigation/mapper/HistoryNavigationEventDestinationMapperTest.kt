package com.mitteloupe.whoami.navigation.mapper

import androidx.navigation.NavController
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent.Back
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(Parameterized::class)
class HistoryNavigationEventDestinationMapperTest(
    private val givenNavigationEvent: PresentationNavigationEvent,
    private val assertExpectation: (NavController) -> Unit
) {
    companion object {
        @JvmStatic
        @Parameters(name = "Given {0}")
        fun data(): Iterable<Array<*>> = setOf(
            testCase(Back) { navController ->
                verify(navController).navigateUp()
            }
        )

        private fun testCase(
            navigationEvent: PresentationNavigationEvent,
            assertExpectation: (NavController) -> Unit
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
        val navController: NavController = mock()

        // When
        val destination = classUnderTest.toUi(givenNavigationEvent)
        destination.navigate(navController)

        // Then
        assertExpectation(navController)
    }
}
