package com.mitteloupe.whoami.architecture.presentation.viewmodel

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.architecture.domain.exception.DomainException
import com.mitteloupe.whoami.architecture.domain.usecase.UseCase
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.presentation.notification.PresentationNotification
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<VIEW_STATE : Any, NOTIFICATION : PresentationNotification>(
    private val useCaseExecutor: UseCaseExecutor
) {
    val viewState: Flow<VIEW_STATE>
        field = MutableSharedFlow()

    val notification: Flow<NOTIFICATION>
        field = MutableSharedFlow()

    val navigationEvent: Flow<PresentationNavigationEvent>
        field = MutableSharedFlow()

    protected fun updateViewState(newState: VIEW_STATE) {
        MainScope().launch {
            viewState.emit(newState)
        }
    }

    protected fun notify(notification: NOTIFICATION) {
        MainScope().launch {
            this@BaseViewModel.notification.emit(notification)
        }
    }

    protected fun emitNavigationEvent(navigationEvent: PresentationNavigationEvent) {
        MainScope().launch {
            this@BaseViewModel.navigationEvent.emit(navigationEvent)
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
