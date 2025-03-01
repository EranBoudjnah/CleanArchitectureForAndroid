package com.mitteloupe.whoami.datasource.history.mapper

import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordLocalModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.MethodRule
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.mockito.junit.MockitoJUnit

@RunWith(Parameterized::class)
class SavedIpAddressRecordDataMapperTest(
    @Suppress("unused") private val testTitle: String,
    private val givenHistoryRecord: SavedIpAddressHistoryRecordLocalModel,
    private val expectedSavedRecord: SavedIpAddressHistoryRecordDataModel
) {
    companion object {
        @JvmStatic
        @Parameters(name = "Given {0}, time then returns {2}")
        fun data(): Collection<Array<*>> = listOf(
            testCase(
                testTitle = "all nulls",
                ipAddress = "1.1.1.1",
                city = null,
                region = null,
                countryCode = null,
                geolocation = null,
                internetServiceProviderName = null,
                postCode = null,
                timeZone = null,
                savedAtTimestampMilliseconds = 123L
            ),
            testCase(
                testTitle = "all set",
                ipAddress = "2.2.2.2",
                city = "City",
                region = "Region",
                countryCode = "CC",
                geolocation = "#.#,#.#",
                internetServiceProviderName = "ISP",
                postCode = "PS CD1",
                timeZone = "GMT",
                savedAtTimestampMilliseconds = 321L
            )
        )

        private fun testCase(
            testTitle: String,
            ipAddress: String,
            city: String?,
            region: String?,
            countryCode: String?,
            geolocation: String?,
            internetServiceProviderName: String?,
            postCode: String?,
            timeZone: String?,
            savedAtTimestampMilliseconds: Long
        ) = arrayOf(
            testTitle,
            SavedIpAddressHistoryRecordLocalModel(
                ipAddress = ipAddress,
                city = city,
                region = region,
                countryCode = countryCode,
                geolocation = geolocation,
                internetServiceProviderName = internetServiceProviderName,
                postCode = postCode,
                timeZone = timeZone,
                savedAtTimestampMilliseconds = savedAtTimestampMilliseconds
            ),
            SavedIpAddressHistoryRecordDataModel(
                ipAddress = ipAddress,
                city = city,
                region = region,
                countryCode = countryCode,
                geolocation = geolocation,
                internetServiceProviderName = internetServiceProviderName,
                postCode = postCode,
                timeZone = timeZone,
                savedAtTimestampMilliseconds = savedAtTimestampMilliseconds
            )
        )
    }

    @get:Rule
    val mockitoRule: MethodRule = MockitoJUnit.rule()

    private lateinit var classUnderTest: SavedIpAddressRecordDataMapper

    @Before
    fun setUp() {
        classUnderTest = SavedIpAddressRecordDataMapper()
    }

    @Test
    fun `When toSaved`() {
        // When
        val actualValue = classUnderTest.toData(givenHistoryRecord)

        // Then
        assertEquals(expectedSavedRecord, actualValue)
    }
}
