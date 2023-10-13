package com.mitteloupe.whoami.datasource.history.datasource

import com.mitteloupe.whoami.datasource.history.model.HistoryRecordDeletionIdentifierDataModel
import com.mitteloupe.whoami.datasource.history.model.NewIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordDataModel
import kotlinx.coroutines.flow.Flow

interface IpAddressHistoryDataSource {
    fun save(record: NewIpAddressHistoryRecordDataModel)

    fun delete(deletionIdentifier: HistoryRecordDeletionIdentifierDataModel)

    fun allRecords(): Flow<Collection<SavedIpAddressHistoryRecordDataModel>>
}
