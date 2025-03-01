package com.mitteloupe.whoami.home.presentation.viewmodel

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.architecture.presentation.viewmodel.BaseViewModel
import com.mitteloupe.whoami.home.domain.usecase.GetConnectionDetailsUseCase
import com.mitteloupe.whoami.home.domain.usecase.SaveConnectionDetailsUseCase
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionDetailsPresentationMapper
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionDetailsToDomainMapper
import com.mitteloupe.whoami.home.presentation.mapper.ExceptionPresentationMapper
import com.mitteloupe.whoami.home.presentation.model.HomePresentationNotification
import com.mitteloupe.whoami.home.presentation.model.HomePresentationNotification.ConnectionSaved
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.presentation.model.HomeViewState.Error
import com.mitteloupe.whoami.home.presentation.model.HomeViewState.Loading
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnSavedDetails
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewHistory
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewOpenSourceNotices

class HomeViewModel(
    private val getConnectionDetailsUseCase: GetConnectionDetailsUseCase,
    private val connectionDetailsPresentationMapper: ConnectionDetailsPresentationMapper,
    private val saveConnectionDetailsUseCase: SaveConnectionDetailsUseCase,
    private val connectionDetailsToDomainMapper: ConnectionDetailsToDomainMapper,
    private val exceptionPresentationMapper: ExceptionPresentationMapper,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel<HomeViewState, HomePresentationNotification>(useCaseExecutor, Loading) {
    fun onEnter() {
        updateViewState(Loading)
        getConnectionDetailsUseCase(
            onResult = { result ->
                updateViewState(
                    connectionDetailsPresentationMapper.toPresentation(result)
                )
            },
            onException = { exception ->
                updateViewState(Error(exceptionPresentationMapper.toPresentation(exception)))
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
                    OnSavedDetails(highlightedIpAddress = connectionDetails.ipAddress)
                )
            },
            onException = { exception ->
                updateViewState(Error(exceptionPresentationMapper.toPresentation(exception)))
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
