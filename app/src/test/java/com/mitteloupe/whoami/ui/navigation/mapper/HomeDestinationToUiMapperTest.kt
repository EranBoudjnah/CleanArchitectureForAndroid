package com.mitteloupe.whoami.ui.navigation.mapper

import android.content.Context
import android.content.Intent
import androidx.navigation.NavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationDestination
import com.mitteloupe.whoami.architecture.ui.navigation.exception.UnhandledDestinationException
import com.mitteloupe.whoami.home.presentation.navigation.ViewHistoryPresentationDestination
import com.mitteloupe.whoami.home.presentation.navigation.ViewOpenSourceNoticesPresentationDestination
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
    fun `Given ViewHistory when toUi then returns history navigation`() {
        // Given
        val presentationDestination = ViewHistoryPresentationDestination
        val navController: NavController = mock()
        val uiDestination = classUnderTest.toUi(presentationDestination)

        // When
        uiDestination.navigate(navController)

        // Then
        verify(navController).navigate("history")
    }

    @Test
    fun `Given ViewOpenSourceNotices when toUi then returns open source notices navigation`() {
        // Given
        val presentationDestination = ViewOpenSourceNoticesPresentationDestination
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
        val presentationDestination: PresentationDestination = mock()
        var caughtException: Exception? = null

        // When
        try {
            classUnderTest.toUi(presentationDestination)
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
                "^Navigation to PresentationDestination\\\$MockitoMock\\\$\\w+ was not handled.$"
            )
        )
    }
}
