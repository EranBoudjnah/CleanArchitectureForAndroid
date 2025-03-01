package com.mitteloupe.whoami.history.presentation.mapper

import com.mitteloupe.whoami.history.domain.model.SavedIpAddressRecordDomainModel
import com.mitteloupe.whoami.history.presentation.model.SavedIpAddressRecordPresentationModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class SavedIpAddressRecordPresentationMapperTest(
    @Suppress("unused") private val testTitle: String,
    private val givenSavedRecord: SavedIpAddressRecordDomainModel,
    private val expectedSavedRecord: SavedIpAddressRecordPresentationModel
) {
    companion object {
        @JvmStatic
        @Parameters(name = "Given {0} then returns {2}")
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
            SavedIpAddressRecordPresentationModel(
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

    private lateinit var classUnderTest: SavedIpAddressRecordPresentationMapper

    @Before
    fun setUp() {
        classUnderTest = SavedIpAddressRecordPresentationMapper()
    }

    @Test
    fun `When toPresentation`() {
        // Given

        // When
        val actualValue = classUnderTest.toPresentation(givenSavedRecord)

        // Then
        assertEquals(expectedSavedRecord, actualValue)
    }
}
