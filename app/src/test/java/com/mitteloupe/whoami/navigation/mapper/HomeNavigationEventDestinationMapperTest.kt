package com.mitteloupe.whoami.navigation.mapper

import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.ui.navigation.exception.UnhandledNavigationException
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnSavedDetails
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewHistory
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewOpenSourceNotices
import com.mitteloupe.whoami.ui.main.route.History
import com.mitteloupe.whoami.ui.main.route.OpenSourceNotices
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.isA
import org.hamcrest.Matchers.matchesPattern
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class HomeNavigationEventDestinationMapperTest {
    private lateinit var classUnderTest: HomeNavigationEventDestinationMapper

    @Before
    fun setUp() {
        classUnderTest = HomeNavigationEventDestinationMapper()
    }

    @Test
    fun `Given OnViewHistory when toUi then returns history navigation with no highlight`() {
        // Given
        val presentationDestination = OnViewHistory
        val backStack = mutableListOf<Any>()
        val uiDestination = classUnderTest.toUi(presentationDestination)
        val expectedDestination = History(null)
        val expectedBackStack = listOf(expectedDestination)

        // When
        uiDestination.navigate(backStack)

        // Then
        assertEquals(expectedBackStack, backStack)
    }

    @Test
    fun `Given OnSavedDetails when toUi then returns history navigation with given highlight`() {
        // Given
        val ipAddress = "1.2.3.4"
        val presentationDestination = OnSavedDetails(ipAddress)
        val backStack = mutableListOf<Any>()
        val uiDestination = classUnderTest.toUi(presentationDestination)
        val expectedDestination = History(ipAddress)
        val expectedBackStack = listOf(expectedDestination)

        // When
        uiDestination.navigate(backStack)

        // Then
        assertEquals(expectedBackStack, backStack)
    }

    @Test
    fun `Given OnViewOpenSourceNotices when toUi then returns open source notices navigation`() {
        // Given
        val presentationDestination = OnViewOpenSourceNotices
        val uiDestination = classUnderTest.toUi(presentationDestination)
        val backStack = mutableListOf<Any>()
        val expectedDestination = OpenSourceNotices
        val expectedBackStack = listOf(expectedDestination)

        // When
        uiDestination.navigate(backStack)

        // Then
        assertEquals(expectedBackStack, backStack)
    }

    @Test
    fun `Given unknown destination when toUi then throws meaningful illegal state exception`() {
        // Given
        val presentationNavigationEvent: PresentationNavigationEvent = mock()
        var caughtException: Exception? = null

        // When
        try {
            classUnderTest.toUi(presentationNavigationEvent)
        } catch (exception: Exception) {
            caughtException = exception
        }

        // Then
        val actualException = requireNotNull(caughtException)
        assertThat(
            actualException,
            isA(UnhandledNavigationException::class.java)
        )
        assertThat(
            actualException.message,
            matchesPattern(
                "^Navigation event PresentationNavigationEvent\\\$MockitoMock\\\$\\w+ " +
                    "was not handled.$"
            )
        )
    }
}
