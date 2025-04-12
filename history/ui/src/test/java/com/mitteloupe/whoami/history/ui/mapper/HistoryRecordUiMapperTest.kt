package com.mitteloupe.whoami.history.ui.mapper

import android.content.res.Resources
import com.mitteloupe.whoami.history.presentation.model.SavedIpAddressRecordPresentationModel
import com.mitteloupe.whoami.history.ui.R
import com.mitteloupe.whoami.history.ui.model.HistoryRecordUiModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.MethodRule
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.given

private const val UNKNOWN_LOCATION_TEXT = "Unknown location"

@RunWith(Parameterized::class)
class HistoryRecordUiMapperTest(
    @Suppress("unused") private val testTitle: String,
    private val givenSavedRecord: SavedIpAddressRecordPresentationModel,
    private val givenHighlightedIpAddress: String,
    private val expectedValue: HistoryRecordUiModel
) {
    companion object {
        @JvmStatic
        @Parameters(name = "{0} then returns {2}")
        fun data(): Iterable<Array<*>> = setOf(
            testCase(
                testTitle = "All nulls",
                ipAddress = "1.2.3.4",
                expectedLocation = UNKNOWN_LOCATION_TEXT
            ),
            testCase(
                testTitle = "Is highlighted",
                ipAddress = "4.3.2.1",
                savedAtTimestampMilliseconds = 10L,
                highlightedIpAddress = "4.3.2.1",
                isHighlighted = true,
                expectedLocation = UNKNOWN_LOCATION_TEXT
            ),
            testCase(
                testTitle = "Another IP is highlighted",
                ipAddress = "4.3.2.1",
                savedAtTimestampMilliseconds = 10L,
                highlightedIpAddress = "0.0.0.0",
                expectedLocation = UNKNOWN_LOCATION_TEXT
            ),
            testCase(
                testTitle = "Only city",
                ipAddress = "1.2.3.4",
                city = "Testtown",
                postCode = " ",
                expectedLocation = "City: Testtown"
            ),
            testCase(
                testTitle = "Only region",
                ipAddress = "1.2.3.4",
                region = "Some region",
                postCode = " ",
                expectedLocation = "Unknown location"
            ),
            testCase(
                testTitle = "Only country code",
                ipAddress = "1.2.3.4",
                countryCode = "GB",
                postCode = " ",
                expectedLocation = "Unknown location"
            ),
            testCase(
                testTitle = "Only geolocation",
                ipAddress = "1.2.3.4",
                geolocation = "0, 0",
                postCode = " ",
                expectedLocation = "Unknown location"
            ),
            testCase(
                testTitle = "Only internet service provider",
                ipAddress = "1.2.3.4",
                geolocation = "0, 0",
                internetServiceProviderName = "Golden Internet Provider",
                postCode = " ",
                expectedLocation = "Unknown location"
            ),
            testCase(
                testTitle = "Only postcode",
                ipAddress = "1.2.3.4",
                city = " ",
                postCode = "12345",
                expectedLocation = "Post code: 12345"
            ),
            testCase(
                testTitle = "City and postcode",
                ipAddress = "1.2.3.4",
                city = "Successville",
                postCode = "12345",
                expectedLocation = "Successville - 12345"
            ),
            testCase(
                testTitle = "Only time zone",
                ipAddress = "1.2.3.4",
                timeZone = "GMT",
                expectedLocation = "Unknown location"
            )
        )

        private fun testCase(
            testTitle: String,
            ipAddress: String,
            city: String? = null,
            region: String? = null,
            countryCode: String? = null,
            geolocation: String? = null,
            internetServiceProviderName: String? = null,
            postCode: String? = null,
            timeZone: String? = null,
            savedAtTimestampMilliseconds: Long = 0L,
            highlightedIpAddress: String = "",
            isHighlighted: Boolean = false,
            expectedLocation: String
        ) = arrayOf(
            testTitle,
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
            ),
            highlightedIpAddress,
            HistoryRecordUiModel(
                ipAddress,
                expectedLocation,
                savedAtTimestampMilliseconds,
                isHighlighted
            )
        )
    }

    @get:Rule
    val mockitoRule: MethodRule = MockitoJUnit.rule()

    private lateinit var classUnderTest: HistoryRecordUiMapper

    @Mock
    private lateinit var resources: Resources

    @Before
    fun setUp() {
        givenStringResources()

        classUnderTest = HistoryRecordUiMapper(resources)
    }

    @Test
    fun `When toUi`() {
        // When
        val actualValue = classUnderTest.toUi(givenSavedRecord, givenHighlightedIpAddress)

        // Then
        assertEquals(expectedValue, actualValue)
    }

    private fun givenStringResources() {
        given(resources.getString(eq(R.string.history_record_location_unknown)))
            .willReturn(UNKNOWN_LOCATION_TEXT)
        given(resources.getString(eq(R.string.history_record_location_city_format), any<String>()))
            .willReturn("City: ${givenSavedRecord.city}")
        given(
            resources.getString(eq(R.string.history_record_location_postcode_format), any<String>())
        ).willReturn("Post code: ${givenSavedRecord.postCode}")
        given(
            resources.getString(
                eq(R.string.history_record_location_full_format),
                any<String>(),
                any<String>()
            )
        ).willReturn("${givenSavedRecord.city} - ${givenSavedRecord.postCode}")
    }
}
