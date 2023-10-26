package com.mitteloupe.whoami.history.ui.mapper

import com.mitteloupe.whoami.history.presentation.model.HistoryRecordDeletionPresentationModel
import com.mitteloupe.whoami.history.ui.model.HistoryRecordUiModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class HistoryRecordDeletionToPresentationMapperTest(
    private val givenRecord: HistoryRecordUiModel,
    private val expectedRequest: HistoryRecordDeletionPresentationModel
) {
    companion object {
        @JvmStatic
        @Parameters(name = "Given {0} then returns {1}")
        fun data(): Collection<Array<*>> = listOf(
            testCase("0.0.0.0"),
            testCase("255.255.255.255")
        )

        private fun testCase(ipAddress: String) = arrayOf(
            HistoryRecordUiModel(
                ipAddress = ipAddress,
                location = "Home",
                savedAtTimestampMilliseconds = 123L
            ),
            HistoryRecordDeletionPresentationModel(ipAddress)
        )
    }

    private lateinit var classUnderTest: HistoryRecordDeletionToPresentationMapper

    @Before
    fun setUp() {
        classUnderTest = HistoryRecordDeletionToPresentationMapper()
    }

    @Test
    fun `When toDeletionPresentation`() {
        // When
        val actualValue = classUnderTest.toDeletionPresentation(givenRecord)

        // Then
        assertEquals(expectedRequest, actualValue)
    }
}
