package com.mitteloupe.whoami.home.presentation.viewmodel

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.architecture.domain.exception.DomainException
import com.mitteloupe.whoami.architecture.presentation.viewmodel.BaseViewModel
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import com.mitteloupe.whoami.home.domain.usecase.GetConnectionDetailsUseCase
import com.mitteloupe.whoami.home.domain.usecase.SaveConnectionDetailsUseCase
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionDetailsDomainMapper
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionDetailsPresentationMapper
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
    private val connectionDetailsDomainMapper: ConnectionDetailsDomainMapper,
    private val exceptionPresentationMapper: ExceptionPresentationMapper,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel<HomeViewState, HomePresentationNotification>(useCaseExecutor, Loading) {
    fun onEnter() {
        updateViewState(Loading)
        fetchConnectionDetails()
    }

    fun onSaveDetailsAction(connectionDetails: HomeViewState.Connected) {
        val domainConnectionDetails = connectionDetailsDomainMapper.toDomain(connectionDetails)
        saveConnectionDetails(domainConnectionDetails)
    }

    fun onViewHistoryAction() {
        emitNavigationEvent(OnViewHistory)
    }

    fun onOpenSourceNoticesAction() {
        emitNavigationEvent(OnViewOpenSourceNotices)
    }

    private fun fetchConnectionDetails() {
        getConnectionDetailsUseCase(
            onResult = ::presentConnectionDetails,
            onException = ::presentError
        )
    }

    private fun presentConnectionDetails(connectionDetails: ConnectionDetailsDomainModel) {
        updateViewState(connectionDetailsPresentationMapper.toPresentation(connectionDetails))
    }

    private fun saveConnectionDetails(connectionDetails: ConnectionDetailsDomainModel.Connected) {
        saveConnectionDetailsUseCase(
            value = connectionDetails,
            onResult = { presentSaveDetailsResult(connectionDetails.ipAddress) },
            onException = ::presentError
        )
    }

    private fun presentSaveDetailsResult(ipAddress: String) {
        notify(ConnectionSaved(ipAddress))
        emitNavigationEvent(OnSavedDetails(highlightedIpAddress = ipAddress))
    }

    private fun presentError(exception: DomainException) {
        updateViewState(Error(exceptionPresentationMapper.toPresentation(exception)))
    }
}
