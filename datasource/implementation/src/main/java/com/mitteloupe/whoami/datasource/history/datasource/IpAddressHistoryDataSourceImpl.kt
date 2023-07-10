package com.mitteloupe.whoami.datasource.history.datasource

import com.mitteloupe.whoami.datasource.history.mapper.NewIpAddressRecordToSavedMapper
import com.mitteloupe.whoami.datasource.history.model.NewIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordDataModel

class IpAddressHistoryDataSourceImpl(
    private val newIpAddressRecordToSavedMapper: NewIpAddressRecordToSavedMapper
) : IpAddressHistoryDataSource {
    private val historyRecords: MutableMap<String, SavedIpAddressHistoryRecordDataModel> =
        mutableMapOf()

    override fun save(record: NewIpAddressHistoryRecordDataModel) {
        val savedRecord = newIpAddressRecordToSavedMapper.toSaved(record)
        historyRecords[record.ipAddress] = savedRecord
    }

    override fun allRecords(): Collection<SavedIpAddressHistoryRecordDataModel> =
        historyRecords.values
}
