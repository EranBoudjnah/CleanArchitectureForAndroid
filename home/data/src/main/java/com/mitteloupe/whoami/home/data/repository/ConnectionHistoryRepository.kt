package com.mitteloupe.whoami.home.data.repository

import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSource
import com.mitteloupe.whoami.home.data.mapper.ConnectionDetailsToDataMapper
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import com.mitteloupe.whoami.home.domain.repository.SaveConnectionDetailsRepository

class ConnectionHistoryRepository(
    private val ipAddressHistoryDataSource: IpAddressHistoryDataSource,
    private val connectionDetailsToDataMapper: ConnectionDetailsToDataMapper
) : SaveConnectionDetailsRepository {
    override fun saveConnectionDetails(details: ConnectionDetailsDomainModel.Connected) {
        val dataConnectionDetails = connectionDetailsToDataMapper.toData(details)
        ipAddressHistoryDataSource.save(dataConnectionDetails)
    }
}
