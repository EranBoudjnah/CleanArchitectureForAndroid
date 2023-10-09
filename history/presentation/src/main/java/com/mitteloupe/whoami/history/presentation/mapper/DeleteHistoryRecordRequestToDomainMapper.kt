package com.mitteloupe.whoami.history.presentation.mapper

import com.mitteloupe.whoami.history.domain.model.HistoryRecordDeletionDomainModel
import com.mitteloupe.whoami.history.presentation.model.HistoryRecordDeletionPresentationModel

class DeleteHistoryRecordRequestToDomainMapper {
    fun toDomain(deletionRequest: HistoryRecordDeletionPresentationModel) =
        HistoryRecordDeletionDomainModel(deletionRequest.ipAddress)
}
