package com.mitteloupe.whoami.history.ui.mapper

import com.mitteloupe.whoami.history.presentation.model.SavedIpAddressRecordPresentationModel
import com.mitteloupe.whoami.history.ui.model.HistoryRecordUiModel

class HistoryRecordToUiMapper {
    fun toUi(savedRecord: SavedIpAddressRecordPresentationModel) =
        HistoryRecordUiModel(ipAddress = savedRecord.ipAddress)
}
