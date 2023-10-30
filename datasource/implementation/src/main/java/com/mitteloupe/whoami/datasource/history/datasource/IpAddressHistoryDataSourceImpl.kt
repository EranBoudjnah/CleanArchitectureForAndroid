package com.mitteloupe.whoami.datasource.history.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import com.mitteloupe.whoami.datasource.history.mapper.NewIpAddressRecordToLocalMapper
import com.mitteloupe.whoami.datasource.history.mapper.SavedIpAddressRecordToDataMapper
import com.mitteloupe.whoami.datasource.history.model.HistoryRecordDeletionIdentifierDataModel
import com.mitteloupe.whoami.datasource.history.model.NewIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordLocalModel
import com.mitteloupe.whoami.datasource.json.JsonDecoder
import com.mitteloupe.whoami.datasource.json.JsonEncoder
import com.mitteloupe.whoami.datasource.local.LocalStoreKey.KEY_HISTORY_RECORDS
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.runBlocking

class IpAddressHistoryDataSourceImpl(
    private val newIpAddressRecordToLocalMapper: NewIpAddressRecordToLocalMapper,
    private val savedIpAddressRecordToDataMapper: SavedIpAddressRecordToDataMapper,
    private val sharedPreferences: SharedPreferences,
    private val jsonEncoder: JsonEncoder<Map<String, SavedIpAddressHistoryRecordLocalModel>>,
    private val jsonDecoder: JsonDecoder<Map<String, SavedIpAddressHistoryRecordLocalModel>>
) : IpAddressHistoryDataSource {
    private val historyRecords: MutableMap<String, SavedIpAddressHistoryRecordLocalModel> by lazy {
        sharedPreferences.getString(KEY_HISTORY_RECORDS, null)?.let { recordsString ->
            val decodedRecords = jsonDecoder.decode(recordsString)
            decodedRecords
        }.orEmpty().toMutableMap()
    }

    private val mutableHistoryRecordsFlow =
        MutableSharedFlow<Map<String, SavedIpAddressHistoryRecordLocalModel>>(replay = 1)

    private val historyRecordsFlow = mutableHistoryRecordsFlow.onStart {
        mutableHistoryRecordsFlow.emitRecords()
    }

    override fun save(record: NewIpAddressHistoryRecordDataModel) {
        val savedRecord = newIpAddressRecordToLocalMapper.toLocal(record)
        historyRecords[record.ipAddress] = savedRecord
        sharedPreferences.edit {
            putString(KEY_HISTORY_RECORDS, jsonEncoder.encode(historyRecords))
        }
        mutableHistoryRecordsFlow.emitRecords()
    }

    override fun delete(deletionIdentifier: HistoryRecordDeletionIdentifierDataModel) {
        historyRecords.remove(deletionIdentifier.ipAddress)
        mutableHistoryRecordsFlow.emitRecords()
    }

    override fun allRecords() =
        historyRecordsFlow.map { historyRecords ->
            historyRecords.values.map(savedIpAddressRecordToDataMapper::toData)
        }

    private fun FlowCollector<Map<String, SavedIpAddressHistoryRecordLocalModel>>.emitRecords() {
        runBlocking {
            emit(historyRecords)
        }
    }
}
