package com.mitteloupe.whoami.home.ui.mapper

import android.content.Context
import com.mitteloupe.whoami.home.presentation.model.HomePresentationNotification
import com.mitteloupe.whoami.home.presentation.model.HomePresentationNotification.ConnectionSaved
import com.mitteloupe.whoami.home.ui.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.MethodRule
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.whenever

@RunWith(Parameterized::class)
class HomeNotificationUiMapperTest(
    @Suppress("unused")
    private val description: String,
    private val givenPresentationNotification: HomePresentationNotification,
    private val givenStub: (Context) -> Unit,
    private val expectedNotificationAssertion: (Context, (Context, String) -> Unit) -> Unit
) {
    companion object {
        private const val SAVED_CONNECTION_IP_ADDRESS = "1.2.3.4"
        private const val SAVED_CONNECTION_MESSAGE = "$SAVED_CONNECTION_IP_ADDRESS saved!"

        @JvmStatic
        @Parameters(name = "Given {0}")
        fun data(): Collection<Array<*>> = listOf(
            testCase(
                "connection saved notification then presents toast",
                ConnectionSaved(SAVED_CONNECTION_IP_ADDRESS),
                { context ->
                    whenever(
                        context.getString(
                            R.string.home_details_saved_notification,
                            SAVED_CONNECTION_IP_ADDRESS
                        )
                    ).thenReturn(SAVED_CONNECTION_MESSAGE)
                },
                { context: Context, notificationToast: (Context, String) -> Unit ->
                    verify(notificationToast).invoke(context, SAVED_CONNECTION_MESSAGE)
                }
            )
        )

        private fun testCase(
            description: String,
            givenPresentationNotification: HomePresentationNotification,
            givenStub: (Context) -> Unit,
            expectedNotificationAssertion: (Context, (Context, String) -> Unit) -> Unit
        ) = arrayOf(
            description,
            givenPresentationNotification,
            givenStub,
            expectedNotificationAssertion
        )
    }

    @get:Rule
    val mockitoRule: MethodRule = MockitoJUnit.rule()

    private lateinit var classUnderTest: HomeNotificationUiMapper

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var notificationToast: (Context, String) -> Unit

    @Before
    fun setUp() {
        classUnderTest = HomeNotificationUiMapper(context, notificationToast)
    }

    @Test
    fun `When toUi, notification presented`() {
        // Given
        givenStub(context)

        // When
        val uiNotification = classUnderTest.toUi(givenPresentationNotification)
        uiNotification.present()

        // Then
        expectedNotificationAssertion(context, notificationToast)
    }
}
