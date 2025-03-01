package com.mitteloupe.whoami.datasource.ipaddressinformation.datasource

import com.mitteloupe.whoami.datasource.ipaddressinformation.exception.NoIpAddressInformationDataException
import com.mitteloupe.whoami.datasource.ipaddressinformation.mapper.IpAddressInformationDataMapper
import com.mitteloupe.whoami.datasource.ipaddressinformation.model.IpAddressInformationApiModel
import com.mitteloupe.whoami.datasource.ipaddressinformation.model.IpAddressInformationDataModel
import com.mitteloupe.whoami.datasource.ipaddressinformation.service.IpAddressInformationService
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
class IpAddressInformationDataSourceImplTest {
    private lateinit var classUnderTest: IpAddressInformationDataSourceImpl

    private lateinit var lazyIpAddressInformationService: Lazy<IpAddressInformationService>

    @Mock
    private lateinit var ipAddressInformationService: IpAddressInformationService

    @Mock
    private lateinit var ipAddressInformationDataMapper: IpAddressInformationDataMapper

    @Before
    fun setUp() {
        lazyIpAddressInformationService = lazy { ipAddressInformationService }

        classUnderTest = IpAddressInformationDataSourceImpl(
            lazyIpAddressInformationService,
            ipAddressInformationDataMapper
        )
    }

    @Test
    fun `Given ip address, connection details when ipAddressInformation then returns details`() {
        // Given
        val ipAddress = "9.9.9.9"
        val city = "Paris"
        val region = "Paris"
        val country = "France"
        val geolocation = "2.345,5.432"
        val internetServiceProviderName = "Le Internet De Monde"
        val postCode = "12345"
        val timeZone = "GMT +1"
        val givenIpAddressResponse = IpAddressInformationApiModel(
            city = city,
            region = region,
            country = country,
            geolocation = geolocation,
            internetServiceProviderName = internetServiceProviderName,
            postCode = postCode,
            timeZone = timeZone
        )
        val givenResponse: Response<IpAddressInformationApiModel> = mock {
            on { body() } doReturn givenIpAddressResponse
        }
        val givenServerResponse: Call<IpAddressInformationApiModel> = mock {
            on { execute() } doReturn givenResponse
        }
        given { ipAddressInformationService.ipAddressInformation(ipAddress) }
            .willReturn(givenServerResponse)
        val expectedIpAddressInformation = IpAddressInformationDataModel(
            city = city,
            region = region,
            country = country,
            geolocation = geolocation,
            internetServiceProviderName = internetServiceProviderName,
            postCode = postCode,
            timeZone = timeZone
        )
        given { ipAddressInformationDataMapper.toData(givenIpAddressResponse) }
            .willReturn(expectedIpAddressInformation)

        // When
        val actualValue = classUnderTest.ipAddressInformation(ipAddress)

        // Then
        assertEquals(expectedIpAddressInformation, actualValue)
    }

    @Test(expected = NoIpAddressInformationDataException::class)
    fun `Given ip address, no details when ipAddressInformation then throws exception`() {
        // Given
        val ipAddress = "1.3.3.7"
        val givenResponse: Response<IpAddressInformationApiModel> = mock {
            on { body() } doReturn null
        }
        val givenServerResponse: Call<IpAddressInformationApiModel> = mock {
            on { execute() } doReturn givenResponse
        }
        given { ipAddressInformationService.ipAddressInformation(ipAddress) }
            .willReturn(givenServerResponse)

        // When
        classUnderTest.ipAddressInformation(ipAddress)

        // Then throws NoIpAddressInformationDataException
    }

    @Test(expected = RequestTimeoutDataException::class)
    fun `Given ip address, timeout when ipAddressInformation then throws exception`() {
        // Given
        val ipAddress = "1.3.3.7"
        val givenServerResponse: Call<IpAddressInformationApiModel> = mock {
            on { execute() } doThrow SocketTimeoutException()
        }
        given { ipAddressInformationService.ipAddressInformation(ipAddress) }
            .willReturn(givenServerResponse)

        // When
        classUnderTest.ipAddressInformation(ipAddress)

        // Then throws RequestTimeoutDataException
    }
}
