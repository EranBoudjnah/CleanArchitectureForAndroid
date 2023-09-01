package com.mitteloupe.whoami.architecture.presentation.viewmodel

import com.mitteloupe.whoami.architecture.domain.UseCase
import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.architecture.domain.exception.DomainException
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
    private val mutableViewState by mutableStateFlow { initialViewState }
    val viewState by immutableFlow { mutableViewState }

    private val mutableNotification by mutableSharedFlow<NOTIFICATION>()
    val notification by immutableFlow { mutableNotification }

    private val mutableDestination by mutableSharedFlow<PresentationDestination>()
    val destination by immutableFlow { mutableDestination }

    protected fun updateViewState(newState: VIEW_STATE) {
        MainScope().launch {
            mutableViewState.emit(newState)
        }
    }

    protected fun notify(notification: NOTIFICATION) {
        MainScope().launch {
            mutableNotification.emit(notification)
        }
    }

    protected fun navigate(destination: PresentationDestination) {
        MainScope().launch {
            mutableDestination.emit(destination)
        }
    }

    protected fun navigateBack() {
        MainScope().launch {
            mutableDestination.emit(PresentationDestination.Back)
        }
    }

    protected fun <OUTPUT> UseCase<Unit, OUTPUT>.run(
        onResult: (OUTPUT) -> Unit = {},
        onException: (DomainException) -> Unit = {}
    ) {
        useCaseExecutor.execute(this, onResult, onException)
    }

    protected fun <INPUT, OUTPUT> UseCase<INPUT, OUTPUT>.run(
        value: INPUT,
        onResult: (OUTPUT) -> Unit = {},
        onException: (DomainException) -> Unit = {}
    ) {
        useCaseExecutor.execute(this, value, onResult, onException)
    }

    private fun <T> mutableStateFlow(initialValueProvider: () -> T) =
        lazy { MutableStateFlow(initialValueProvider()) }

    private fun <T> mutableSharedFlow() = lazy { MutableSharedFlow<T>() }

    private fun <T, FLOW : MutableSharedFlow<T>> immutableFlow(
        initializer: () -> FLOW
    ): Lazy<Flow<T>> = lazy { initializer() }
}
