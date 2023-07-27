package com.mitteloupe.whoami.datasource.history.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import com.mitteloupe.whoami.datasource.history.mapper.NewIpAddressRecordToLocalMapper
import com.mitteloupe.whoami.datasource.history.mapper.SavedIpAddressRecordToDataMapper
import com.mitteloupe.whoami.datasource.history.model.NewIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordLocalModel
import com.mitteloupe.whoami.datasource.json.JsonDecoder
import com.mitteloupe.whoami.datasource.json.JsonEncoder
import com.mitteloupe.whoami.datasource.local.LocalStoreKey.KEY_HISTORY_RECORDS

class IpAddressHistoryDataSourceImpl(
    private val newIpAddressRecordToLocalMapper: NewIpAddressRecordToLocalMapper,
    private val savedIpAddressRecordToDataMapper: SavedIpAddressRecordToDataMapper,
    private val sharedPreferences: SharedPreferences,
    private val jsonEncoder: JsonEncoder<Map<String, SavedIpAddressHistoryRecordLocalModel>>,
    private val jsonDecoder: JsonDecoder<Map<String, SavedIpAddressHistoryRecordLocalModel>>
) : IpAddressHistoryDataSource {
    private val historyRecords: MutableMap<String, SavedIpAddressHistoryRecordLocalModel> by lazy {
        sharedPreferences.getString(KEY_HISTORY_RECORDS, null)?.let { recordsString ->
            val decode = jsonDecoder.decode(recordsString)
            val toMutableMap = decode?.toMutableMap()
            toMutableMap
        } ?: mutableMapOf()
    }

    override fun save(record: NewIpAddressHistoryRecordDataModel) {
        val savedRecord = newIpAddressRecordToLocalMapper.toLocal(record)
        historyRecords[record.ipAddress] = savedRecord
        sharedPreferences.edit {
            putString(KEY_HISTORY_RECORDS, jsonEncoder.encode(historyRecords))
        }
    }

    override fun allRecords(): Collection<SavedIpAddressHistoryRecordDataModel> =
        historyRecords.values.map(savedIpAddressRecordToDataMapper::toData)
}
