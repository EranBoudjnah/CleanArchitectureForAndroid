package com.mitteloupe.whoami.history.data.repository

import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSource
import com.mitteloupe.whoami.history.data.mapper.HistoryRecordDeletionDataMapper
import com.mitteloupe.whoami.history.data.mapper.SavedIpAddressRecordDomainMapper
import com.mitteloupe.whoami.history.domain.model.HistoryRecordDeletionDomainModel
import com.mitteloupe.whoami.history.domain.repository.DeleteHistoryRecordRepository
import com.mitteloupe.whoami.history.domain.repository.GetHistoryRepository
import kotlinx.coroutines.flow.map

class ConnectionHistoryRepository(
    private val ipAddressHistoryDataSource: IpAddressHistoryDataSource,
    private val savedIpAddressRecordDomainMapper: SavedIpAddressRecordDomainMapper,
    private val recordDeletionToDataMapper: HistoryRecordDeletionDataMapper
) : GetHistoryRepository,
    DeleteHistoryRecordRepository {
    override fun history() = ipAddressHistoryDataSource.allRecords()
        .map { records ->
            records.map(savedIpAddressRecordDomainMapper::toDomain)
        }

    override fun delete(record: HistoryRecordDeletionDomainModel) {
        val dataRequest = recordDeletionToDataMapper.toData(record)
        ipAddressHistoryDataSource.delete(dataRequest)
    }
}
