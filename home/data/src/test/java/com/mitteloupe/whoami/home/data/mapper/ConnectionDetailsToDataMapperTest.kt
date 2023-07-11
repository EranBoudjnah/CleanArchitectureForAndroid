package com.mitteloupe.whoami.home.data.mapper

import com.mitteloupe.whoami.datasource.history.model.NewIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class ConnectionDetailsToDataMapperTest(
    @Suppress("unused") private val testTitle: String,
    private val givenconnectionDetails: ConnectionDetailsDomainModel.Connected,
    private val expectedHistoryRecord: NewIpAddressHistoryRecordDataModel
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
                timeZone = null
            ),
            testCase(
                testTitle = "all set",
                ipAddress = "2.2.2.2",
                city = "Prague",
                region = "",
                countryCode = "CZ",
                geolocation = "2.0,2.0",
                internetServiceProviderName = "CzechNet",
                postCode = "",
                timeZone = ""
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
            timeZone: String?
        ) = arrayOf(
            testTitle,
            ConnectionDetailsDomainModel.Connected(
                ipAddress = ipAddress,
                city = city,
                region = region,
                countryCode = countryCode,
                geolocation = geolocation,
                internetServiceProviderName = internetServiceProviderName,
                postCode = postCode,
                timeZone = timeZone
            ),
            NewIpAddressHistoryRecordDataModel(
                ipAddress = ipAddress,
                city = city,
                region = region,
                countryCode = countryCode,
                geolocation = geolocation,
                internetServiceProviderName = internetServiceProviderName,
                postCode = postCode,
                timeZone = timeZone
            )
        )
    }

    private lateinit var classUnderTest: ConnectionDetailsToDataMapper

    @Before
    fun setUp() {
        classUnderTest = ConnectionDetailsToDataMapper()
    }

    @Test
    fun `When toData`() {
        // Given

        // When
        val actualValue = classUnderTest.toData(givenconnectionDetails)

        // Then
        assertEquals(expectedHistoryRecord, actualValue)
    }
}
