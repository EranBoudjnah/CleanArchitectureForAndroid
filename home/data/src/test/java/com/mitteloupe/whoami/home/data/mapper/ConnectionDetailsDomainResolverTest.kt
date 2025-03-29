package com.mitteloupe.whoami.home.data.mapper

import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel.Connected
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel.Disconnected
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel.Unset
import com.mitteloupe.whoami.datasource.ipaddressinformation.model.IpAddressInformationDataModel
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

private const val IP_ADDRESS = "1.2.3.4"

@RunWith(Parameterized::class)
class ConnectionDetailsDomainResolverTest(
    @Suppress("unused") private val testTitle: String,
    private val connectionState: ConnectionStateDataModel,
    private val ipAddressInformation: IpAddressInformationDataModel,
    private val expectedDomainModel: ConnectionDetailsDomainModel
) {
    companion object {
        @JvmStatic
        @Parameters(name = "Given {0} then returns {3}")
        fun data(): Iterable<Array<*>> = setOf(
            connectedWithTestCase(
                testTitle = "no information",
                city = null,
                region = null,
                countryCode = null,
                geolocation = null,
                internetServiceProviderName = null,
                postCode = null,
                timeZone = null
            ),
            connectedWithTestCase(
                testTitle = "information",
                city = "London",
                region = "Greater London",
                countryCode = "United Kingdom",
                geolocation = "0.1,0.1",
                internetServiceProviderName = "MegaFibre",
                postCode = "E17 1CO",
                timeZone = "GMT"
            ),
            testCase(
                testTitle = "disconnected",
                connectionState = Disconnected,
                expectedDomainModel = ConnectionDetailsDomainModel.Disconnected
            ),
            testCase(
                testTitle = "unset",
                connectionState = Unset,
                expectedDomainModel = ConnectionDetailsDomainModel.Unset
            )
        )

        private fun connectedWithTestCase(
            testTitle: String,
            city: String?,
            region: String?,
            countryCode: String?,
            geolocation: String?,
            internetServiceProviderName: String?,
            postCode: String?,
            timeZone: String?
        ) = testCase(
            "connected with $testTitle",
            Connected,
            IpAddressInformationDataModel(
                city = city,
                region = region,
                country = countryCode,
                geolocation = geolocation,
                internetServiceProviderName = internetServiceProviderName,
                postCode = postCode,
                timeZone = timeZone
            ),
            ConnectionDetailsDomainModel.Connected(
                ipAddress = IP_ADDRESS,
                city = city,
                region = region,
                countryCode = countryCode,
                geolocation = geolocation,
                internetServiceProviderName = internetServiceProviderName,
                postCode = postCode,
                timeZone = timeZone
            )
        )

        private fun testCase(
            testTitle: String,
            connectionState: ConnectionStateDataModel,
            ipAddressInformation: IpAddressInformationDataModel = IpAddressInformationDataModel(
                city = null,
                region = null,
                country = null,
                geolocation = null,
                internetServiceProviderName = null,
                postCode = null,
                timeZone = null
            ),
            expectedDomainModel: ConnectionDetailsDomainModel
        ) = arrayOf(
            testTitle,
            connectionState,
            ipAddressInformation,
            expectedDomainModel
        )
    }

    private lateinit var classUnderTest: ConnectionDetailsDomainResolver

    @Before
    fun setUp() {
        classUnderTest = ConnectionDetailsDomainResolver()
    }

    @Test
    fun `When toDomain`() {
        // When
        val actualValue = classUnderTest.toDomain(
            connectionState,
            IP_ADDRESS,
            ipAddressInformation
        )

        // Then
        assertEquals(expectedDomainModel, actualValue)
    }
}
