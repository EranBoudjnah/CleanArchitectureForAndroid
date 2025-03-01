package com.mitteloupe.whoami.home.data.repository

import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSource
import com.mitteloupe.whoami.home.data.mapper.ConnectionDetailsDataMapper
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import com.mitteloupe.whoami.home.domain.repository.SaveConnectionDetailsRepository

class ConnectionHistoryRepository(
    private val ipAddressHistoryDataSource: IpAddressHistoryDataSource,
    private val connectionDetailsDataMapper: ConnectionDetailsDataMapper
) : SaveConnectionDetailsRepository {
    override fun saveConnectionDetails(details: ConnectionDetailsDomainModel.Connected) {
        val dataConnectionDetails = connectionDetailsDataMapper.toData(details)
        ipAddressHistoryDataSource.save(dataConnectionDetails)
    }
}
