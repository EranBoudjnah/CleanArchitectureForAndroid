package com.mitteloupe.whoami.datasource.history.mapper

import com.mitteloupe.whoami.datasource.history.model.NewIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordLocalModel
import com.mitteloupe.whoami.time.NowProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.MethodRule
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit

@RunWith(Parameterized::class)
class NewIpAddressRecordToLocalMapperTest(
    @Suppress("unused") private val testTitle: String,
    private val givenHistoryRecord: NewIpAddressHistoryRecordDataModel,
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
            NewIpAddressHistoryRecordDataModel(
                ipAddress = ipAddress,
                city = city,
                region = region,
                countryCode = countryCode,
                geolocation = geolocation,
                internetServiceProviderName = internetServiceProviderName,
                postCode = postCode,
                timeZone = timeZone
            ),
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
            )
        )
    }

    @get:Rule
    val mockitoRule: MethodRule = MockitoJUnit.rule()

    private lateinit var classUnderTest: NewIpAddressRecordToLocalMapper

    @Mock
    private lateinit var nowProvider: NowProvider

    @Before
    fun setUp() {
        classUnderTest = NewIpAddressRecordToLocalMapper(nowProvider)
    }

    @Test
    fun `When toSaved`() {
        // Given
        given(nowProvider.nowMilliseconds())
            .willReturn(expectedSavedRecord.savedAtTimestampMilliseconds)

        // When
        val actualValue = classUnderTest.toLocal(givenHistoryRecord)

        // Then
        assertEquals(expectedSavedRecord, actualValue)
    }
}
