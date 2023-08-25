package com.mitteloupe.whoami.ui.navigation.mapper

import android.content.Context
import android.content.Intent
import androidx.navigation.NavHostController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationDestination
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
    private lateinit var activityContext: Context

    @Mock
    private lateinit var ossLicensesMenuIntentProvider: Context.(Class<out Any>) -> Intent

    @Before
    fun setUp() {
        classUnderTest = HomeDestinationToUiMapper(activityContext, ossLicensesMenuIntentProvider)
    }

    @Test
    fun `Given ViewHistory when toUi then returns history navigation`() {
        // Given
        val presentationDestination = ViewHistoryPresentationDestination
        val navController = givenSetNavHostController()
        val uiDestination = classUnderTest.toUi(presentationDestination)

        // When
        uiDestination.navigate()

        // Then
        verify(navController).navigate("history")
    }

    @Test
    fun `Given ViewOpenSourceNotices when toUi then returns open source notices navigation`() {
        // Given
        val presentationDestination = ViewOpenSourceNoticesPresentationDestination
        val uiDestination = classUnderTest.toUi(presentationDestination)
        val expectedIntent: Intent = mock()
        given(
            ossLicensesMenuIntentProvider.invoke(
                eq(activityContext),
                eq(OssLicensesMenuActivity::class.java)
            )
        ).willReturn(expectedIntent)

        // When
        uiDestination.navigate()

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
            isA(IllegalStateException::class.java)
        )
        assertThat(
            actualException.message,
            matchesPattern(
                "^Unknown destination: Mock for PresentationDestination, hashCode: \\d+$"
            )
        )
    }

    private fun givenSetNavHostController(): NavHostController {
        val navController: NavHostController = mock()
        classUnderTest.setNavController(navController)
        return navController
    }
}
