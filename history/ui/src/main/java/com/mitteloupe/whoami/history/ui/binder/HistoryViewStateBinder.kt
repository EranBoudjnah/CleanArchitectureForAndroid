package com.mitteloupe.whoami.history.ui.binder

import android.os.Build
import android.os.Bundle
import androidx.core.view.isVisible
import com.mitteloupe.whoami.architecture.ui.binder.ViewStateBinder
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState
import com.mitteloupe.whoami.history.ui.adapter.HistoryAdapter
import com.mitteloupe.whoami.history.ui.mapper.HistoryRecordToUiMapper
import com.mitteloupe.whoami.history.ui.model.HistoryRecordUiModel
import com.mitteloupe.whoami.history.ui.view.HistoryViewsProvider

private const val HISTORY_RECORDS_BUNDLE_KEY = "historyRecords"

class HistoryViewStateBinder(
    private val historyRecordToUiMapper: HistoryRecordToUiMapper
) : ViewStateBinder<HistoryViewState, HistoryViewsProvider> {
    private val historyAdapter = HistoryAdapter()
    private val historyItems: MutableList<HistoryRecordUiModel> = mutableListOf()

    fun Bundle.saveState() {
        putParcelableArray(HISTORY_RECORDS_BUNDLE_KEY, historyItems.toTypedArray())
    }

    fun Bundle.restoreState(viewsProvider: HistoryViewsProvider) {
        val savedHistoryItems = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getParcelableArray(HISTORY_RECORDS_BUNDLE_KEY, HistoryRecordUiModel::class.java)
        } else {
            @Suppress("DEPRECATION", "UNCHECKED_CAST")
            getParcelableArray(HISTORY_RECORDS_BUNDLE_KEY) as Array<HistoryRecordUiModel>?
        }
        viewsProvider.setUpAdapterAndBindItems(savedHistoryItems?.toList().orEmpty())
    }

    override fun HistoryViewsProvider.bindState(viewState: HistoryViewState) {
        when (viewState) {
            is HistoryViewState.HistoryRecords -> {
                noRecordsView.isVisible = viewState.historyRecords.isEmpty()
                recordsListView.isVisible = viewState.historyRecords.isNotEmpty()
                val historyItems = viewState.historyRecords.map(historyRecordToUiMapper::toUi)
                    .sortedBy { historyRecord -> historyRecord.ipAddress }
                setUpAdapterAndBindItems(historyItems)
            }

            HistoryViewState.Loading -> {
                noRecordsView.isVisible = false
                recordsListView.isVisible = false
            }
        }
    }

    private fun HistoryViewsProvider.setUpAdapterAndBindItems(
        newHistoryItems: List<HistoryRecordUiModel>
    ) {
        historyItems.clear()
        historyItems.addAll(newHistoryItems)
        recordsListView.adapter = historyAdapter
        historyAdapter.setItems(historyItems)
    }
}
