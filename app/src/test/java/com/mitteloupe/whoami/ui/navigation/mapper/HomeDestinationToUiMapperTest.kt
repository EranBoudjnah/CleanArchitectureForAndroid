package com.mitteloupe.whoami.ui.navigation.mapper

import android.content.Context
import android.content.Intent
import androidx.navigation.NavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.ui.navigation.exception.UnhandledDestinationException
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnSaveDetails
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewHistory
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewOpenSourceNotices
import com.mitteloupe.whoami.ui.main.route.History
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.isA
import org.hamcrest.Matchers.matchesPattern
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.eq
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class HomeDestinationToUiMapperTest {
    private lateinit var classUnderTest: HomeDestinationToUiMapper

    @Mock
    private lateinit var analytics: Analytics

    @Mock
    private lateinit var activityContext: Context

    @Mock
    private lateinit var ossLicensesMenuIntentProvider: Context.(Class<out Any>) -> Intent

    @Before
    fun setUp() {
        classUnderTest =
            HomeDestinationToUiMapper(analytics, activityContext, ossLicensesMenuIntentProvider)
    }

    @Test
    fun `Given OnViewHistory when toUi then returns history navigation with no highlight`() {
        // Given
        val presentationDestination = OnViewHistory
        val navController: NavController = mock()
        val uiDestination = classUnderTest.toUi(presentationDestination)
        val expectedDestination = History(null)

        // When
        uiDestination.navigate(navController)

        // Then
        verify(navController).navigate(expectedDestination)
    }

    @Test
    fun `Given OnSaveDetails when toUi then returns history navigation with given highlight`() {
        // Given
        val ipAddress = "1.2.3.4"
        val presentationDestination = OnSaveDetails(ipAddress)
        val navController: NavController = mock()
        val uiDestination = classUnderTest.toUi(presentationDestination)
        val expectedDestination = History(ipAddress)

        // When
        uiDestination.navigate(navController)

        // Then
        verify(navController).navigate(expectedDestination)
    }

    @Test
    fun `Given OnViewOpenSourceNotices when toUi then returns open source notices navigation`() {
        // Given
        val presentationDestination = OnViewOpenSourceNotices
        val uiDestination = classUnderTest.toUi(presentationDestination)
        val expectedIntent: Intent = mock()
        val navController: NavController = mock()
        given(
            ossLicensesMenuIntentProvider.invoke(
                eq(activityContext),
                eq(OssLicensesMenuActivity::class.java)
            )
        ).willReturn(expectedIntent)

        // When
        uiDestination.navigate(navController)

        // Then
        verify(activityContext).startActivity(expectedIntent)
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
            isA(UnhandledDestinationException::class.java)
        )
        assertThat(
            actualException.message,
            matchesPattern(
                "^Navigation to PresentationNavigationEvent\\\$MockitoMock\\\$\\w+ was not handled.$"
            )
        )
    }
}
