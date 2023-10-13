package com.mitteloupe.whoami.history.domain.repository

import com.mitteloupe.whoami.history.domain.model.HistoryRecordDeletionDomainModel

interface DeleteHistoryRecordRepository {
    fun delete(record: HistoryRecordDeletionDomainModel)
}
