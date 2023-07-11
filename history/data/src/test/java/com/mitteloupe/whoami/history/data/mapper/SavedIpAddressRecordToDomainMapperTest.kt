package com.mitteloupe.whoami.history.data.mapper

import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.history.domain.model.SavedIpAddressRecordDomainModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class SavedIpAddressRecordToDomainMapperTest(
    @Suppress("unused") private val testTitle: String,
    private val givenDataSavedRecord: SavedIpAddressHistoryRecordDataModel,
    private val expectedDomainRecord: SavedIpAddressRecordDomainModel
) {
    companion object {
        @JvmStatic
        @Parameters(name = "Given {0} then returns {2}")
        fun data(): Collection<Array<*>> = listOf(
            testCase(
                testTitle = "all set",
                ipAddress = "1.2.3.4",
                city = "Tel Aviv",
                region = "HaMerkaz",
                countryCode = "IL",
                geolocation = "0.0,0.0",
                internetServiceProviderName = "Kavey Zahav",
                postCode = "123456",
                timeZone = "GMT+200",
                savedAtTimestampMilliseconds = 111L
            ),
            testCase(
                testTitle = "all null",
                ipAddress = "4.3.2.1",
                city = null,
                region = null,
                countryCode = null,
                geolocation = null,
                internetServiceProviderName = null,
                postCode = null,
                timeZone = null,
                savedAtTimestampMilliseconds = 222L
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
            ),
            SavedIpAddressRecordDomainModel(
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
        )
    }

    private lateinit var classUnderTest: SavedIpAddressRecordToDomainMapper

    @Before
    fun setUp() {
        classUnderTest = SavedIpAddressRecordToDomainMapper()
    }

    @Test
    fun `When toDomain`() {
        // When
        val actualValue = classUnderTest.toDomain(givenDataSavedRecord)

        // Then
        assertEquals(expectedDomainRecord, actualValue)
    }
}
