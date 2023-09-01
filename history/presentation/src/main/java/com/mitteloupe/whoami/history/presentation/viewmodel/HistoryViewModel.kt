package com.mitteloupe.whoami.history.presentation.viewmodel

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationDestination.Back
import com.mitteloupe.whoami.architecture.presentation.notification.PresentationNotification
import com.mitteloupe.whoami.architecture.presentation.viewmodel.BaseViewModel
import com.mitteloupe.whoami.history.domain.usecase.GetHistoryUseCase
import com.mitteloupe.whoami.history.presentation.mapper.SavedIpAddressRecordToPresentationMapper
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState.HistoryRecords
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState.Loading
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState.NoChange

class HistoryViewModel(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val savedIpAddressRecordToPresentationMapper: SavedIpAddressRecordToPresentationMapper,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel<HistoryViewState, PresentationNotification>(useCaseExecutor) {
    override val initialViewState = NoChange

    fun onEnter() {
        updateViewState(Loading)
        getHistoryUseCase.run(
            onResult = { result ->
                updateViewState(
                    HistoryRecords(
                        result.map(savedIpAddressRecordToPresentationMapper::toPresentation)
                    )
                )
            },
            onException = { exception ->
                println("UNEXPECTED: $exception")
            }
        )
    }

    fun onBackAction() {
        navigate(Back)
    }
}
