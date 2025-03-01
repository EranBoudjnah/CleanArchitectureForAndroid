package com.mitteloupe.whoami.history.presentation.mapper

import com.mitteloupe.whoami.history.domain.model.HistoryRecordDeletionDomainModel
import com.mitteloupe.whoami.history.presentation.model.HistoryRecordDeletionPresentationModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class DeleteHistoryRecordRequestDomainMapperTest(
    @Suppress("unused") private val testTitle: String,
    private val givenDeleteRequest: HistoryRecordDeletionPresentationModel,
    private val expectedDeletionRequest: HistoryRecordDeletionDomainModel
) {
    companion object {
        @JvmStatic
        @Parameters(name = "Given {0} then returns {2}")
        fun data(): Collection<Array<*>> = listOf(
            testCase(
                testTitle = "case 1",
                ipAddress = "1.1.1.1"
            ),
            testCase(
                testTitle = "case 2",
                ipAddress = "255.255.255.255"
            )
        )

        private fun testCase(testTitle: String, ipAddress: String) = arrayOf(
            testTitle,
            HistoryRecordDeletionPresentationModel(ipAddress = ipAddress),
            HistoryRecordDeletionDomainModel(ipAddress = ipAddress)
        )
    }

    private lateinit var classUnderTest: DeleteHistoryRecordRequestDomainMapper

    @Before
    fun setUp() {
        classUnderTest = DeleteHistoryRecordRequestDomainMapper()
    }

    @Test
    fun `When toDomain`() {
        // Given

        // When
        val actualValue = classUnderTest.toDomain(givenDeleteRequest)

        // Then
        assertEquals(expectedDeletionRequest, actualValue)
    }
}
