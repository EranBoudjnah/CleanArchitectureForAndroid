package com.mitteloupe.whoami.architecture.presentation

import com.mitteloupe.whoami.architecture.domain.UseCase
import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.architecture.domain.exception.DomainException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<VIEW_STATE : Any, NOTIFICATION : Any>(
    private val useCaseExecutor: UseCaseExecutor
) {
    protected abstract val initialViewState: VIEW_STATE
    private val mutableViewState by lazy {
        MutableStateFlow(initialViewState)
    }
    val viewState: Flow<VIEW_STATE> by lazy {
        mutableViewState
    }

    protected fun updateViewState(newState: VIEW_STATE) {
        MainScope().launch(Dispatchers.Main) {
            mutableViewState.emit(newState)
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
}
