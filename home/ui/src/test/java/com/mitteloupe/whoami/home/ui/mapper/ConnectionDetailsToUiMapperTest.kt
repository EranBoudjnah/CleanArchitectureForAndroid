package com.mitteloupe.whoami.home.ui.mapper

import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.ui.R
import com.mitteloupe.whoami.home.ui.model.ConnectionDetailsUiModel
import com.mitteloupe.whoami.home.ui.model.IconLabelUiModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class ConnectionDetailsToUiMapperTest(
    @Suppress("unused") private val testTitle: String,
    private val presentationConnectionDetails: HomeViewState.Connected,
    private val expectedUiConnectionDetails: ConnectionDetailsUiModel
) {
    companion object {
        @JvmStatic
        @Parameters(name = "Given {0}")
        fun data(): Collection<Array<*>> = listOf(
            testCase(
                testTitle = "case 1",
                ipAddress = "1.2.3.4",
                city = "Tel Aviv",
                region = "HaMerkaz",
                countryCode = "IL",
                countryName = "Israel",
                geolocation = "0.0,0.0",
                formattedGeolocation = "0.0, 0.0",
                internetServiceProviderName = "Kavey Zahav",
                postCode = "123456",
                timeZone = "GMT+200"
            ),
            testCase(
                testTitle = "case 2",
                ipAddress = "4.3.2.1",
                city = "London",
                region = "London",
                countryCode = "GB",
                countryName = "Great Britain",
                geolocation = "1.0,1.0",
                formattedGeolocation = "1.0, 1.0",
                internetServiceProviderName = "Royal WiFi",
                postCode = "AA11 2CC",
                timeZone = "GMT"
            ),
            nullsTestCase(ipAddress = "4.3.2.1")
        )

        private fun testCase(
            testTitle: String,
            ipAddress: String,
            city: String,
            region: String,
            countryCode: String,
            countryName: String,
            geolocation: String,
            formattedGeolocation: String,
            internetServiceProviderName: String,
            postCode: String,
            timeZone: String
        ) = arrayOf(
            testTitle,
            HomeViewState.Connected(
                ipAddress = ipAddress,
                city = city,
                region = region,
                countryCode = countryCode,
                geolocation = geolocation,
                internetServiceProviderName = internetServiceProviderName,
                postCode = postCode,
                timeZone = timeZone
            ),
            ConnectionDetailsUiModel(
                ipAddress = ipAddress,
                cityIconLabel = IconLabelUiModel(R.drawable.icon_city, city),
                regionIconLabel = IconLabelUiModel(R.drawable.icon_region, region),
                countryIconLabel = IconLabelUiModel(R.drawable.icon_country, countryName),
                geolocationIconLabel = IconLabelUiModel(
                    R.drawable.icon_geolocation,
                    formattedGeolocation
                ),
                postCode = IconLabelUiModel(R.drawable.icon_post_code, postCode),
                timeZone = IconLabelUiModel(R.drawable.icon_time_zone, timeZone),
                internetServiceProviderName = IconLabelUiModel(
                    R.drawable.icon_internet_service_provider,
                    internetServiceProviderName
                )
            )
        )

        private fun nullsTestCase(ipAddress: String) = arrayOf(
            "all nulls",
            HomeViewState.Connected(
                ipAddress = ipAddress,
                city = null,
                region = null,
                countryCode = null,
                geolocation = null,
                internetServiceProviderName = null,
                postCode = null,
                timeZone = null
            ),
            ConnectionDetailsUiModel(
                ipAddress = ipAddress,
                cityIconLabel = null,
                regionIconLabel = null,
                countryIconLabel = null,
                geolocationIconLabel = null,
                postCode = null,
                timeZone = null,
                internetServiceProviderName = null
            )
        )
    }

    private lateinit var classUnderTest: ConnectionDetailsToUiMapper

    private lateinit var toCountryName: String.() -> String

    @Before
    fun setUp() {
        toCountryName = {
            assertEquals(this, presentationConnectionDetails.countryCode)
            val countryIconLabel = expectedUiConnectionDetails.countryIconLabel
            requireNotNull(countryIconLabel)
            countryIconLabel.label
        }
        classUnderTest = ConnectionDetailsToUiMapper(toCountryName)
    }

    @Test
    fun `When toUi then returns expected UI connection state`() {
        // Given

        // When
        val actualValue = classUnderTest.toUi(presentationConnectionDetails)

        // Then
        assertEquals(expectedUiConnectionDetails, actualValue)
    }
}
