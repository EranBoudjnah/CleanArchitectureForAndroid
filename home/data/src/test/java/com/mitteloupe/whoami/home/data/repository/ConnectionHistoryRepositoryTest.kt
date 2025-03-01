package com.mitteloupe.whoami.home.data.repository

import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSource
import com.mitteloupe.whoami.datasource.history.model.NewIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.home.data.mapper.ConnectionDetailsDataMapper
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ConnectionHistoryRepositoryTest {
    private lateinit var classUnderTest: ConnectionHistoryRepository

    @MockK(relaxed = true)
    private lateinit var ipAddressHistoryDataSource: IpAddressHistoryDataSource

    @MockK
    private lateinit var connectionDetailsDataMapper: ConnectionDetailsDataMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        classUnderTest =
            ConnectionHistoryRepository(ipAddressHistoryDataSource, connectionDetailsDataMapper)
    }

    @Test
    fun `Given connection details when saveConnectionDetails then saves connection details`() {
        // Given
        val ipAddress = "1.1.1.1"
        val details = ConnectionDetailsDomainModel.Connected(
            ipAddress = ipAddress,
            city = null,
            region = null,
            countryCode = null,
            geolocation = null,
            internetServiceProviderName = null,
            postCode = null,
            timeZone = null
        )
        val dataRecord = NewIpAddressHistoryRecordDataModel(
            ipAddress = ipAddress,
            city = null,
            region = null,
            countryCode = null,
            geolocation = null,
            internetServiceProviderName = null,
            postCode = null,
            timeZone = null
        )
        every { connectionDetailsDataMapper.toData(details) } returns dataRecord

        // When
        classUnderTest.saveConnectionDetails(details)

        // Then
        verify { ipAddressHistoryDataSource.save(dataRecord) }
    }
}
