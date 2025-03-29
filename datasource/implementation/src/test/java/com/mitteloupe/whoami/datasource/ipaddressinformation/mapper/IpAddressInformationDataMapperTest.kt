package com.mitteloupe.whoami.datasource.ipaddressinformation.mapper

import com.mitteloupe.whoami.datasource.ipaddressinformation.model.IpAddressInformationApiModel
import com.mitteloupe.whoami.datasource.ipaddressinformation.model.IpAddressInformationDataModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class IpAddressInformationDataMapperTest(
    private val toDataIpAddress: IpAddressInformationApiModel,
    private val toDataExpected: IpAddressInformationDataModel
) {
    companion object {
        @JvmStatic
        @Parameters(name = "Given {0} then returns {1}")
        fun data(): Iterable<Array<*>> = setOf(
            testCase(
                city = "New York",
                region = "New York",
                country = "United States of America",
                geolocation = "-1.00,-1.00",
                internetServiceProviderName = "Very Horizon",
                postCode = "C12345",
                timeZone = "EST"
            ),
            testCase(
                city = null,
                region = null,
                country = null,
                geolocation = null,
                internetServiceProviderName = null,
                postCode = null,
                timeZone = null
            )
        )

        private fun testCase(
            city: String?,
            region: String?,
            country: String?,
            geolocation: String?,
            internetServiceProviderName: String?,
            postCode: String?,
            timeZone: String?
        ) = arrayOf(
            IpAddressInformationApiModel(
                city = city,
                region = region,
                country = country,
                geolocation = geolocation,
                internetServiceProviderName = internetServiceProviderName,
                postCode = postCode,
                timeZone = timeZone
            ),
            IpAddressInformationDataModel(
                city = city,
                region = region,
                country = country,
                geolocation = geolocation,
                internetServiceProviderName = internetServiceProviderName,
                postCode = postCode,
                timeZone = timeZone
            )
        )
    }

    private lateinit var classUnderTest: IpAddressInformationDataMapper

    @Before
    fun setUp() {
        classUnderTest = IpAddressInformationDataMapper()
    }

    @Test
    fun `When toData`() {
        // Given

        // When
        val actualValue = classUnderTest.toData(toDataIpAddress)

        // Then
        assertEquals(toDataExpected, actualValue)
    }
}
