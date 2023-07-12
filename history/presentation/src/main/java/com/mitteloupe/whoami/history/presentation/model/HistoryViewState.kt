package com.mitteloupe.whoami.history.presentation.model

sealed interface HistoryViewState {
    object Loading : HistoryViewState

    data class HistoryRecords(
        val historyRecords: Collection<SavedIpAddressRecordPresentationModel>
    ) : HistoryViewState
}
