package com.mitteloupe.whoami.home.presentation.viewmodel

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.architecture.presentation.BaseViewModel
import com.mitteloupe.whoami.home.domain.usecase.GetConnectionDetailsUseCase
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionStateToPresentationMapper
import com.mitteloupe.whoami.home.presentation.mapper.ExceptionToPresentationMapper
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.presentation.model.HomeViewState.Error
import com.mitteloupe.whoami.home.presentation.model.HomeViewState.Loading

class HomeViewModel(
    private val getConnectionDetailsUseCase: GetConnectionDetailsUseCase,
    private val connectionStateToPresentationMapper: ConnectionStateToPresentationMapper,
    private val exceptionToPresentationMapper: ExceptionToPresentationMapper,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel<HomeViewState, Any>(useCaseExecutor) {
    override val initialViewState = Loading

    fun onEnter() {
        updateViewState(Loading)
        getConnectionDetailsUseCase.run(
            onResult = { result ->
                updateViewState(
                    connectionStateToPresentationMapper.toPresentation(result)
                )
            },
            onException = { exception ->
                updateViewState(Error(exceptionToPresentationMapper.toPresentation(exception)))
            }
        )
    }
}
