package com.mitteloupe.whoami.architecture.presentation.viewmodel

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.architecture.domain.exception.DomainException
import com.mitteloupe.whoami.architecture.domain.usecase.UseCase
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationDestination
import com.mitteloupe.whoami.architecture.presentation.notification.PresentationNotification
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<VIEW_STATE : Any, NOTIFICATION : PresentationNotification>(
    private val useCaseExecutor: UseCaseExecutor
) {
    protected abstract val initialViewState: VIEW_STATE
    private val mutableViewState by lazy { MutableStateFlow(initialViewState) }
    val viewState: Flow<VIEW_STATE> by lazy { mutableViewState }

    val notification: Flow<NOTIFICATION>
        field = MutableSharedFlow()

    val destination: Flow<PresentationDestination>
        field = MutableSharedFlow()

    protected fun updateViewState(newState: VIEW_STATE) {
        MainScope().launch {
            mutableViewState.emit(newState)
        }
    }

    protected fun notify(notification: NOTIFICATION) {
        MainScope().launch {
            this@BaseViewModel.notification.emit(notification)
        }
    }

    protected fun navigate(destination: PresentationDestination) {
        MainScope().launch {
            this@BaseViewModel.destination.emit(destination)
        }
    }

    protected fun navigateBack() {
        MainScope().launch {
            destination.emit(PresentationDestination.Back)
        }
    }

    protected operator fun <OUTPUT> UseCase<Unit, OUTPUT>.invoke(
        onResult: (OUTPUT) -> Unit = {},
        onException: (DomainException) -> Unit = {}
    ) {
        useCaseExecutor.execute(this, onResult, onException)
    }

    protected operator fun <INPUT, OUTPUT> UseCase<INPUT, OUTPUT>.invoke(
        value: INPUT,
        onResult: (OUTPUT) -> Unit = {},
        onException: (DomainException) -> Unit = {}
    ) {
        useCaseExecutor.execute(this, value, onResult, onException)
    }
}
