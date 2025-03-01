package com.mitteloupe.whoami.datasource.ipaddress.mapper

import com.mitteloupe.whoami.datasource.ipaddress.model.IpAddressApiModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class IpAddressDataMapperTest(
    private val givenApiIpAddress: IpAddressApiModel,
    private val expectedIpAddress: String
) {
    companion object {
        @JvmStatic
        @Parameters(name = "Given {0}")
        fun data(): Collection<Array<*>> = listOf(
            testCase("1.1.1.1"),
            testCase("2.3.4.5")
        )

        private fun testCase(ipAddress: String) = arrayOf(
            IpAddressApiModel(ipAddress),
            ipAddress
        )
    }

    private lateinit var classUnderTest: IpAddressDataMapper

    @Before
    fun setUp() {
        classUnderTest = IpAddressDataMapper()
    }

    @Test
    fun `When toData then returns expected IP address`() {
        // When
        val actualValue = classUnderTest.toData(givenApiIpAddress)

        // Then
        assertEquals(expectedIpAddress, actualValue)
    }
}
