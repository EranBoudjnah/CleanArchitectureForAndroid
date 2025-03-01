package com.mitteloupe.whoami.history.data.mapper

import com.mitteloupe.whoami.datasource.history.model.HistoryRecordDeletionIdentifierDataModel
import com.mitteloupe.whoami.history.domain.model.HistoryRecordDeletionDomainModel

class HistoryRecordDeletionDataMapper {
    fun toData(deletionRequest: HistoryRecordDeletionDomainModel) =
        HistoryRecordDeletionIdentifierDataModel(deletionRequest.ipAddress)
}
