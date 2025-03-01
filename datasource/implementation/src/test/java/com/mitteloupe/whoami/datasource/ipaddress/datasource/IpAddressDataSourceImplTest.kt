package com.mitteloupe.whoami.datasource.ipaddress.datasource

import com.mitteloupe.whoami.datasource.ipaddress.exception.NoIpAddressDataException
import com.mitteloupe.whoami.datasource.ipaddress.mapper.IpAddressDataMapper
import com.mitteloupe.whoami.datasource.ipaddress.model.IpAddressApiModel
import com.mitteloupe.whoami.datasource.ipaddress.service.IpAddressService
import com.mitteloupe.whoami.datasource.remote.exception.RequestTimeoutDataException
import java.net.SocketTimeoutException
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import retrofit2.Call
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class IpAddressDataSourceImplTest {
    private lateinit var classUnderTest: IpAddressDataSourceImpl

    private lateinit var lazyIpAddressService: Lazy<IpAddressService>

    @Mock
    private lateinit var ipAddressService: IpAddressService

    @Mock
    private lateinit var ipAddressDataMapper: IpAddressDataMapper

    @Before
    fun setUp() {
        lazyIpAddressService = lazy { ipAddressService }
        classUnderTest = IpAddressDataSourceImpl(lazyIpAddressService, ipAddressDataMapper)
    }

    @Test
    fun `Given server response when ipAddress then returns IP address`() {
        // Given
        val ipAddress = "8.8.8.8"
        val givenIpAddressResponse = IpAddressApiModel(ipAddress)
        val givenResponse: Response<IpAddressApiModel> = mock {
            on { body() } doReturn givenIpAddressResponse
        }
        val givenServerResponse: Call<IpAddressApiModel> = mock {
            on { execute() } doReturn givenResponse
        }
        given { ipAddressService.ipAddress() }.willReturn(givenServerResponse)
        given { ipAddressDataMapper.toData(givenIpAddressResponse) }.willReturn(ipAddress)

        // When
        val actualValue = classUnderTest.ipAddress()

        // Then
        assertEquals(ipAddress, actualValue)
    }

    @Test(expected = NoIpAddressDataException::class)
    fun `Given null server response when ipAddress then throws NoIpAddressDataException`() {
        // Given
        val givenResponse: Response<IpAddressApiModel> = mock {
            on { body() } doReturn null
        }
        val givenServerResponse: Call<IpAddressApiModel> = mock {
            on { execute() } doReturn givenResponse
        }
        given { ipAddressService.ipAddress() }.willReturn(givenServerResponse)

        // When
        classUnderTest.ipAddress()

        // Then throws NoIpAddressDataException
    }

    @Test(expected = RequestTimeoutDataException::class)
    fun `Given socket timeout exception when ipAddress then throws RequestTimeoutDataException`() {
        // Given
        val givenServerResponse: Call<IpAddressApiModel> = mock {
            on { execute() } doThrow SocketTimeoutException()
        }
        given { ipAddressService.ipAddress() }.willReturn(givenServerResponse)

        // When
        classUnderTest.ipAddress()

        // Then throws NoIpAddressDataException
    }
}
