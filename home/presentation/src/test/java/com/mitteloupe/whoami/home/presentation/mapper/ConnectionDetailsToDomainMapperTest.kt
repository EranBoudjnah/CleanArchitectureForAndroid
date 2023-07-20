package com.mitteloupe.whoami.home.presentation.mapper

import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import com.mitteloupe.whoami.home.presentation.model.HomeViewState.Connected
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.mockito.kotlin.mock

@RunWith(Parameterized::class)
class ConnectionDetailsToDomainMapperTest(
    private val toDomainConnectionDetails: Connected,
    private val toDomainExpected: ConnectionDetailsDomainModel.Connected
) {
    companion object {
        @JvmStatic
        @Parameters(name = "Given {0} then returns {1}")
        fun data(): Collection<Array<*>> = listOf(
            connectedTestCase(
                ipAddress = "1.1.1.1",
                city = null,
                region = null,
                country = null,
                geolocation = null,
                internetServiceProviderName = null,
                postCode = null,
                timeZone = null
            ),
            connectedTestCase(
                ipAddress = "2.2.2.2",
                city = "City",
                region = "Region",
                country = "Country",
                geolocation = "Geolocation",
                internetServiceProviderName = "ISP Name",
                postCode = "Post Code",
                timeZone = "Time Zone"
            )
        )

        private fun connectedTestCase(
            ipAddress: String,
            city: String?,
            region: String?,
            country: String?,
            geolocation: String?,
            internetServiceProviderName: String?,
            postCode: String?,
            timeZone: String?
        ) = arrayOf(
            Connected(
                ipAddress,
                city,
                region,
                country,
                geolocation,
                internetServiceProviderName,
                postCode,
                timeZone
            ),
            ConnectionDetailsDomainModel.Connected(
                ipAddress,
                city,
                region,
                country,
                geolocation,
                internetServiceProviderName,
                postCode,
                timeZone
            )
        )
    }

    private lateinit var classUnderTest: ConnectionDetailsToDomainMapper

    @Before
    fun setUp() {
        classUnderTest = ConnectionDetailsToDomainMapper()
    }

    @Test
    fun `When toDomain`() {
        // Given

        // When
        val actualValue = classUnderTest.toDomain(toDomainConnectionDetails)

        // Then
        assertEquals(toDomainExpected, actualValue)
    }
}
