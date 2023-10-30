package com.mitteloupe.whoami.history.ui.mapper

import com.mitteloupe.whoami.history.presentation.model.HistoryRecordDeletionPresentationModel
import com.mitteloupe.whoami.history.ui.model.HistoryRecordUiModel

class HistoryRecordDeletionToPresentationMapper {
    fun toDeletionPresentation(historyRecord: HistoryRecordUiModel) =
        HistoryRecordDeletionPresentationModel(historyRecord.ipAddress)
}
