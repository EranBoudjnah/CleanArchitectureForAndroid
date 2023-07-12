package com.mitteloupe.whoami.history.presentation.viewmodel

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.architecture.presentation.BaseViewModel
import com.mitteloupe.whoami.history.domain.usecase.GetHistoryUseCase
import com.mitteloupe.whoami.history.presentation.mapper.SavedIpAddressRecordToPresentationMapper
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState.HistoryRecords
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState.Loading

class HistoryViewModel(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val savedIpAddressRecordToPresentationMapper: SavedIpAddressRecordToPresentationMapper,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel<HistoryViewState, Any>(useCaseExecutor) {
    override val initialViewState = Loading

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
}
