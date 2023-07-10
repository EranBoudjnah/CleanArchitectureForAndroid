package com.mitteloupe.whoami.home.data.repository

import com.mitteloupe.whoami.architecture.domain.exception.UnknownDomainException
import com.mitteloupe.whoami.datasource.connection.datasource.ConnectionDataSource
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel
import com.mitteloupe.whoami.datasource.ipaddress.datasource.IpAddressDataSource
import com.mitteloupe.whoami.datasource.ipaddressinformation.datasource.IpAddressInformationDataSource
import com.mitteloupe.whoami.datasource.ipaddressinformation.model.IpAddressInformationDataModel
import com.mitteloupe.whoami.home.data.mapper.ConnectionDetailsToDomainResolver
import com.mitteloupe.whoami.home.data.mapper.ThrowableToDomainMapper
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ConnectionDetailsRepositoryTest {
    private lateinit var classUnderTest: ConnectionDetailsRepository

    @MockK
    private lateinit var ipAddressDataSource: IpAddressDataSource

    @MockK
    private lateinit var ipAddressInformationDataSource: IpAddressInformationDataSource

    @MockK
    private lateinit var connectionDataSource: ConnectionDataSource

    @MockK
    private lateinit var connectionDetailsToDomainResolver: ConnectionDetailsToDomainResolver

    @MockK
    private lateinit var throwableToDomainMapper: ThrowableToDomainMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        classUnderTest = ConnectionDetailsRepository(
            ipAddressDataSource,
            ipAddressInformationDataSource,
            connectionDataSource,
            connectionDetailsToDomainResolver,
            throwableToDomainMapper
        )
    }

    @Test
    fun `Given unset connection when connectionDetails then returns unset state`() = runBlocking {
        // Given
        val givenState = ConnectionStateDataModel.Unset
        every { connectionDataSource.observeIsConnected() } returns flow {
            emit(givenState)
        }
        val expectedState = ConnectionDetailsDomainModel.Unset
        every {
            connectionDetailsToDomainResolver.toDomain(eq(givenState), any(), any())
        } returns expectedState

        // When
        val actualValue = classUnderTest.connectionDetails().toList()

        // Then
        assertEquals(listOf(expectedState), actualValue)
    }

    @Test
    fun `Given disconnected when connectionDetails then returns disconnected state`() =
        runBlocking {
            // Given
            val givenState = ConnectionStateDataModel.Disconnected
            every { connectionDataSource.observeIsConnected() } returns flow {
                emit(givenState)
            }
            val expectedState = ConnectionDetailsDomainModel.Disconnected
            every {
                connectionDetailsToDomainResolver.toDomain(eq(givenState), any(), any())
            } returns expectedState

            // When
            val actualValue = classUnderTest.connectionDetails().toList()

            // Then
            assertEquals(listOf(expectedState), actualValue)
        }

    @Test
    fun `Given connected when connectionDetails then returns connected state`() =
        runBlocking {
            // Given
            val givenState = ConnectionStateDataModel.Connected
            every { connectionDataSource.observeIsConnected() } returns flow {
                emit(givenState)
            }
            val expectedState = ConnectionDetailsDomainModel.Connected(
                ipAddress = "1.2.3.4",
                city = "Paris",
                region = "Paris",
                countryCode = "France",
                geolocation = "0.0,0.0",
                internetServiceProviderName = "Le ISP",
                postCode = "12345",
                timeZone = "GMT +1"
            )

            val ipAddress = "1.1.1.1"

            every {
                connectionDetailsToDomainResolver.toDomain(eq(givenState), any(), any())
            } answers { call ->
                @Suppress("UNCHECKED_CAST")
                testIpAddressReadCorrectly(ipAddress, call.invocation.args[1] as () -> String)
                @Suppress("UNCHECKED_CAST")
                testConnectionDetailsReadCorrectly(
                    ipAddress,
                    call.invocation.args[2] as (String) -> IpAddressInformationDataModel
                )
                expectedState
            }

            // When
            val actualValue = classUnderTest.connectionDetails().toList()

            // Then
            assertEquals(listOf(expectedState), actualValue)
        }

    @Test
    fun `Given throwable, disconnected when connectionDetails then returns error, disconnected`() =
        runBlocking {
            // Given
            val givenState = ConnectionStateDataModel.Connected
            every { connectionDataSource.observeIsConnected() } returns flow {
                emit(givenState)
            }
            val throwable = Throwable()

            val expectedState2 = ConnectionDetailsDomainModel.Disconnected
            every {
                connectionDetailsToDomainResolver.toDomain(eq(givenState), any(), any())
            } throws throwable andThen expectedState2

            val expectedDomainException = UnknownDomainException(throwable)
            every { throwableToDomainMapper.toDomain(throwable) } returns expectedDomainException
            val expectedState1 = ConnectionDetailsDomainModel.Error(expectedDomainException)

            // When
            val actualValue = classUnderTest.connectionDetails().toList()

            // Then
            assertEquals(listOf(expectedState1, expectedState2), actualValue)
        }

    private fun testIpAddressReadCorrectly(
        expectedIpAddress: String,
        ipAddressProvider: () -> String
    ) {
        // Given
        every { ipAddressDataSource.ipAddress() } returns expectedIpAddress

        // When
        val actualIpAddress = ipAddressProvider()

        // Then
        assertEquals(expectedIpAddress, actualIpAddress)
    }

    private fun testConnectionDetailsReadCorrectly(
        ipAddress: String,
        ipAddressInformationProvider: (String) -> IpAddressInformationDataModel
    ) {
        // Given
        val expectedIpAddressInformation = IpAddressInformationDataModel(
            city = "Paris",
            region = "Paris",
            country = "France",
            geolocation = "0.0,0.0",
            internetServiceProviderName = "Le ISP",
            postCode = "12345",
            timeZone = "GMT +1"
        )
        every {
            ipAddressInformationDataSource.ipAddressInformation(ipAddress)
        } returns expectedIpAddressInformation

        // When
        val actualIpAddressInformation = ipAddressInformationProvider(ipAddress)

        // Then
        assertEquals(expectedIpAddressInformation, actualIpAddressInformation)
    }
}
