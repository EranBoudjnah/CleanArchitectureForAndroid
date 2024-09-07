package com.mitteloupe.whoami.home.presentation.viewmodel

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.architecture.presentation.viewmodel.BaseViewModel
import com.mitteloupe.whoami.home.domain.usecase.GetConnectionDetailsUseCase
import com.mitteloupe.whoami.home.domain.usecase.SaveConnectionDetailsUseCase
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionDetailsToDomainMapper
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionStateToPresentationMapper
import com.mitteloupe.whoami.home.presentation.mapper.ExceptionToPresentationMapper
import com.mitteloupe.whoami.home.presentation.model.HomePresentationNotification
import com.mitteloupe.whoami.home.presentation.model.HomePresentationNotification.ConnectionSaved
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.presentation.model.HomeViewState.Error
import com.mitteloupe.whoami.home.presentation.model.HomeViewState.Loading
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnSaveDetails
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewHistory
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewOpenSourceNotices

class HomeViewModel(
    private val getConnectionDetailsUseCase: GetConnectionDetailsUseCase,
    private val connectionStateToPresentationMapper: ConnectionStateToPresentationMapper,
    private val saveConnectionDetailsUseCase: SaveConnectionDetailsUseCase,
    private val connectionDetailsToDomainMapper: ConnectionDetailsToDomainMapper,
    private val exceptionToPresentationMapper: ExceptionToPresentationMapper,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel<HomeViewState, HomePresentationNotification>(useCaseExecutor, Loading) {
    fun onEnter() {
        updateViewState(Loading)
        getConnectionDetailsUseCase(
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

    fun onSaveDetailsAction(connectionDetails: HomeViewState.Connected) {
        val domainConnectionDetails = connectionDetailsToDomainMapper.toDomain(connectionDetails)
        saveConnectionDetailsUseCase(
            value = domainConnectionDetails,
            onResult = {
                notify(ConnectionSaved(connectionDetails.ipAddress))
                emitNavigationEvent(
                    OnSaveDetails(highlightedIpAddress = connectionDetails.ipAddress)
                )
            },
            onException = { exception ->
                updateViewState(Error(exceptionToPresentationMapper.toPresentation(exception)))
            }
        )
    }

    fun onViewHistoryAction() {
        emitNavigationEvent(OnViewHistory)
    }

    fun onOpenSourceNoticesAction() {
        emitNavigationEvent(OnViewOpenSourceNotices)
    }
}
