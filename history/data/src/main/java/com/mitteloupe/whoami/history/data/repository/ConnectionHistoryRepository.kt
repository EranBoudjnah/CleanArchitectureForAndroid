package com.mitteloupe.whoami.history.data.repository

import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSource
import com.mitteloupe.whoami.history.data.mapper.SavedIpAddressRecordToDomainMapper
import com.mitteloupe.whoami.history.domain.repository.GetHistoryRepository

class ConnectionHistoryRepository(
    private val ipAddressHistoryDataSource: IpAddressHistoryDataSource,
    private val savedIpAddressRecordToDomainMapper: SavedIpAddressRecordToDomainMapper
) : GetHistoryRepository {
    override fun history() = ipAddressHistoryDataSource.allRecords()
        .map(savedIpAddressRecordToDomainMapper::toDomain)
}
