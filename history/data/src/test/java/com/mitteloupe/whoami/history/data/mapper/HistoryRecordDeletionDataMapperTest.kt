package com.mitteloupe.whoami.history.data.mapper

import com.mitteloupe.whoami.datasource.history.model.HistoryRecordDeletionIdentifierDataModel
import com.mitteloupe.whoami.history.domain.model.HistoryRecordDeletionDomainModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class HistoryRecordDeletionDataMapperTest(
    private val givenDeletionRequest: HistoryRecordDeletionDomainModel,
    private val expectedDataRequest: HistoryRecordDeletionIdentifierDataModel
) {
    companion object {
        @JvmStatic
        @Parameters(name = "Given {0} then returns {2}")
        fun data(): Iterable<Array<*>> = setOf(
            testCase(ipAddress = "1.2.3.4"),
            testCase(ipAddress = "4.3.2.1")
        )

        private fun testCase(ipAddress: String) = arrayOf(
            HistoryRecordDeletionDomainModel(ipAddress = ipAddress),
            HistoryRecordDeletionIdentifierDataModel(ipAddress = ipAddress)
        )
    }

    private lateinit var classUnderTest: HistoryRecordDeletionDataMapper

    @Before
    fun setUp() {
        classUnderTest = HistoryRecordDeletionDataMapper()
    }

    @Test
    fun `When toData`() {
        // When
        val actualValue = classUnderTest.toData(givenDeletionRequest)

        // Then
        assertEquals(expectedDataRequest, actualValue)
    }
}
