package com.mitteloupe.whoami.history.presentation.viewmodel

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent.Back
import com.mitteloupe.whoami.architecture.presentation.notification.PresentationNotification
import com.mitteloupe.whoami.architecture.presentation.viewmodel.BaseViewModel
import com.mitteloupe.whoami.history.domain.usecase.DeleteHistoryRecordUseCase
import com.mitteloupe.whoami.history.domain.usecase.GetHistoryUseCase
import com.mitteloupe.whoami.history.presentation.mapper.DeleteHistoryRecordRequestDomainMapper
import com.mitteloupe.whoami.history.presentation.mapper.SavedIpAddressRecordPresentationMapper
import com.mitteloupe.whoami.history.presentation.model.HistoryRecordDeletionPresentationModel
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState.HistoryRecords
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState.Loading
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState.NoChange

class HistoryViewModel(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val savedIpAddressRecordPresentationMapper: SavedIpAddressRecordPresentationMapper,
    private val deleteHistoryRecordUseCase: DeleteHistoryRecordUseCase,
    private val deleteHistoryRecordRequestDomainMapper: DeleteHistoryRecordRequestDomainMapper,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel<HistoryViewState, PresentationNotification>(useCaseExecutor, NoChange) {
    fun onEnter(highlightedIpAddress: String?) {
        updateViewState(Loading)
        getHistoryUseCase(
            onResult = { result ->
                updateViewState(
                    HistoryRecords(
                        highlightedIpAddress = highlightedIpAddress,
                        historyRecords = result
                            .map(savedIpAddressRecordPresentationMapper::toPresentation)
                    )
                )
            },
            onException = { exception ->
                println("UNEXPECTED: $exception")
            }
        )
    }

    fun onDeleteAction(deletionRequest: HistoryRecordDeletionPresentationModel) {
        val domainDeletionRequest =
            deleteHistoryRecordRequestDomainMapper.toDomain(deletionRequest)
        deleteHistoryRecordUseCase(
            value = domainDeletionRequest,
            onResult = {},
            onException = { exception ->
                println("UNEXPECTED: $exception")
            }
        )
    }

    fun onBackAction() {
        emitNavigationEvent(Back)
    }
}
