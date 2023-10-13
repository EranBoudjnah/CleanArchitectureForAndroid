package com.mitteloupe.whoami.history.data.mapper

import com.mitteloupe.whoami.datasource.history.model.HistoryRecordDeletionIdentifierDataModel
import com.mitteloupe.whoami.history.domain.model.HistoryRecordDeletionDomainModel

class HistoryRecordDeletionToDataMapper {
    fun toData(deletionRequest: HistoryRecordDeletionDomainModel) =
        HistoryRecordDeletionIdentifierDataModel(deletionRequest.ipAddress)
}
