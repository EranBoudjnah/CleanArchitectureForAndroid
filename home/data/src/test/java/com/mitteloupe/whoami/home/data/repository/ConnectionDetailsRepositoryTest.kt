package com.mitteloupe.whoami.home.data.repository

import com.mitteloupe.whoami.architecture.domain.exception.UnknownDomainException
import com.mitteloupe.whoami.datasource.connection.datasource.ConnectionDataSource
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel
import com.mitteloupe.whoami.datasource.ipaddress.datasource.IpAddressDataSource
import com.mitteloupe.whoami.datasource.ipaddressinformation.datasource.IpAddressInformationDataSource
import com.mitteloupe.whoami.datasource.ipaddressinformation.model.IpAddressInformationDataModel
import com.mitteloupe.whoami.home.data.mapper.ConnectionDetailsDomainResolver
import com.mitteloupe.whoami.home.data.mapper.ThrowableDomainMapper
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

private val defaultIpAddressInformation = IpAddressInformationDataModel(
    city = "Paris",
    region = "Paris",
    country = "France",
    geolocation = "0.0,0.0",
    internetServiceProviderName = "Le ISP",
    postCode = "12345",
    timeZone = "GMT +1"
)

class ConnectionDetailsRepositoryTest {
    private lateinit var classUnderTest: ConnectionDetailsRepository

    @MockK
    private lateinit var ipAddressDataSource: IpAddressDataSource

    @MockK
    private lateinit var ipAddressInformationDataSource: IpAddressInformationDataSource

    @MockK
    private lateinit var connectionDataSource: ConnectionDataSource

    @MockK
    private lateinit var connectionDetailsDomainResolver: ConnectionDetailsDomainResolver

    @MockK
    private lateinit var throwableDomainMapper: ThrowableDomainMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        classUnderTest = ConnectionDetailsRepository(
            ipAddressDataSource,
            ipAddressInformationDataSource,
            connectionDataSource,
            connectionDetailsDomainResolver,
            throwableDomainMapper
        )
    }

    @Test
    fun `Given unset connection when connectionDetails then returns unset state`() = runBlocking {
        // Given
        val givenState = ConnectionStateDataModel.Unset
        givenConnectionState(givenState)
        val expectedState = ConnectionDetailsDomainModel.Unset
        givenConnectionDetailsDomainResolverMaps(givenState, expectedState)

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
            givenConnectionState(givenState)
            val expectedState = ConnectionDetailsDomainModel.Disconnected
            givenConnectionDetailsDomainResolverMaps(givenState, expectedState)

            // When
            val actualValue = classUnderTest.connectionDetails().toList()

            // Then
            assertEquals(listOf(expectedState), actualValue)
        }

    @Test
    fun `Given connected when connectionDetails then returns connected state`() = runBlocking {
        // Given
        val givenState = ConnectionStateDataModel.Connected
        givenConnectionState(givenState)
        val givenIpAddress = "1.2.3.4"
        givenIpAddress(givenIpAddress)
        every {
            ipAddressInformationDataSource.ipAddressInformation(givenIpAddress)
        } returns defaultIpAddressInformation
        val expectedState = ConnectionDetailsDomainModel.Connected(
            ipAddress = givenIpAddress,
            city = "Paris",
            region = "Paris",
            countryCode = "France",
            geolocation = "0.0,0.0",
            internetServiceProviderName = "Le ISP",
            postCode = "12345",
            timeZone = "GMT +1"
        )

        givenConnectionDetailsDomainResolverMaps(givenState, expectedState)

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
            givenConnectionState(givenState)
            val givenIpAddress = "1.1.1.1"
            givenIpAddress(givenIpAddress)
            val throwable = Throwable()
            every {
                ipAddressInformationDataSource.ipAddressInformation(givenIpAddress)
            } throws throwable andThen defaultIpAddressInformation

            val expectedDomainException = UnknownDomainException(throwable)
            every { throwableDomainMapper.toDomain(throwable) } returns expectedDomainException
            val expectedState1 = ConnectionDetailsDomainModel.Error(expectedDomainException)
            val expectedState2 = ConnectionDetailsDomainModel.Disconnected
            givenConnectionDetailsDomainResolverMaps(givenState, expectedState2)

            // When
            val actualValue = classUnderTest.connectionDetails().toList()

            // Then
            assertEquals(listOf(expectedState1, expectedState2), actualValue)
        }

    private fun givenConnectionState(givenState: ConnectionStateDataModel) {
        every { connectionDataSource.observeIsConnected() } returns flow {
            emit(givenState)
        }
    }

    private fun givenConnectionDetailsDomainResolverMaps(
        givenState: ConnectionStateDataModel,
        expectedState: ConnectionDetailsDomainModel
    ) {
        every {
            connectionDetailsDomainResolver.toDomain(eq(givenState), any(), any())
        } returns expectedState
    }

    private fun givenIpAddress(givenIpAddress: String) {
        every { ipAddressDataSource.ipAddress() } returns givenIpAddress
    }
}
