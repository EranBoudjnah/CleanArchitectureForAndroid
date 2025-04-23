package com.mitteloupe.whoami.history.presentation.viewmodel

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent.Back
import com.mitteloupe.whoami.architecture.presentation.notification.PresentationNotification
import com.mitteloupe.whoami.architecture.presentation.viewmodel.BaseViewModel
import com.mitteloupe.whoami.history.domain.model.SavedIpAddressRecordDomainModel
import com.mitteloupe.whoami.history.domain.usecase.DeleteHistoryRecordUseCase
import com.mitteloupe.whoami.history.domain.usecase.GetHistoryUseCase
import com.mitteloupe.whoami.history.presentation.mapper.DeleteHistoryRecordRequestDomainMapper
import com.mitteloupe.whoami.history.presentation.mapper.SavedIpAddressRecordPresentationMapper
import com.mitteloupe.whoami.history.presentation.model.HistoryRecordDeletionPresentationModel
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState.HistoryRecords
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState.Loading

class HistoryViewModel(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val savedIpAddressRecordPresentationMapper: SavedIpAddressRecordPresentationMapper,
    private val deleteHistoryRecordUseCase: DeleteHistoryRecordUseCase,
    private val deleteHistoryRecordRequestDomainMapper: DeleteHistoryRecordRequestDomainMapper,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel<HistoryViewState, PresentationNotification>(useCaseExecutor) {
    fun onEnter(highlightedIpAddress: String?) {
        updateViewState(Loading)
        fetchRecordHistory(highlightedIpAddress)
    }

    fun onDeleteAction(deletionRequest: HistoryRecordDeletionPresentationModel) {
        deleteHistoryRecord(deletionRequest)
    }

    fun onBackAction() {
        emitNavigationEvent(Back)
    }

    private fun fetchRecordHistory(highlightedIpAddress: String?) {
        getHistoryUseCase(
            onResult = { result ->
                presentRecordHistory(
                    highlightedIpAddress = highlightedIpAddress,
                    domainHistoryRecords = result
                )
            },
            onException = { exception ->
                presentRecordHistory(
                    highlightedIpAddress = null,
                    domainHistoryRecords = emptySet()
                )
                println("UNEXPECTED: $exception")
            }
        )
    }

    private fun presentRecordHistory(
        highlightedIpAddress: String?,
        domainHistoryRecords: Collection<SavedIpAddressRecordDomainModel>
    ) {
        val presentationHistoryRecords = domainHistoryRecords
            .map(savedIpAddressRecordPresentationMapper::toPresentation)
        updateViewState(
            HistoryRecords(
                highlightedIpAddress = highlightedIpAddress,
                historyRecords = presentationHistoryRecords
            )
        )
    }

    private fun deleteHistoryRecord(deletionRequest: HistoryRecordDeletionPresentationModel) {
        val domainDeletionRequest = deleteHistoryRecordRequestDomainMapper.toDomain(deletionRequest)
        deleteHistoryRecordUseCase(
            value = domainDeletionRequest,
            onResult = {},
            onException = { exception ->
                println("UNEXPECTED: $exception")
            }
        )
    }
}
