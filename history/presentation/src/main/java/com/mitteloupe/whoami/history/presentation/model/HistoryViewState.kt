package com.mitteloupe.whoami.history.presentation.model

sealed interface HistoryViewState {
    data object NoChange : HistoryViewState

    data object Loading : HistoryViewState

    data class HistoryRecords(
        val highlightedIpAddress: String?,
        val historyRecords: Collection<SavedIpAddressRecordPresentationModel>
    ) : HistoryViewState
}
