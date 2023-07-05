package com.mitteloupe.whoami.home.ui.mapper

import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.ui.model.ConnectionDetailsUiModel
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
                region = "",
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
            testCase(
                testTitle = "all nulls",
                ipAddress = "4.3.2.1",
                city = null,
                region = null,
                countryCode = null,
                countryName = null,
                geolocation = null,
                formattedGeolocation = null,
                internetServiceProviderName = null,
                postCode = null,
                timeZone = null
            )
        )

        private fun testCase(
            testTitle: String,
            ipAddress: String,
            city: String?,
            region: String?,
            countryCode: String?,
            countryName: String?,
            geolocation: String?,
            formattedGeolocation: String?,
            internetServiceProviderName: String?,
            postCode: String?,
            timeZone: String?
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
                city = city,
                region = region,
                countryName = countryName,
                geolocation = formattedGeolocation,
                postCode = postCode,
                timeZone = timeZone,
                internetServiceProviderName = internetServiceProviderName
            )
        )
    }

    private lateinit var classUnderTest: ConnectionDetailsToUiMapper

    private lateinit var toCountryName: String.() -> String

    @Before
    fun setUp() {
        toCountryName = {
            assertEquals(this, presentationConnectionDetails.countryCode)
            val countryName = expectedUiConnectionDetails.countryName
            requireNotNull(countryName)
            countryName
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
