package com.mitteloupe.whoami.architecture.domain

import com.mitteloupe.whoami.architecture.domain.exception.DomainException
import com.mitteloupe.whoami.architecture.domain.exception.UnknownDomainException
import com.mitteloupe.whoami.architecture.domain.usecase.UseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UseCaseExecutor(
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    fun <OUTPUT> execute(
        useCase: UseCase<Unit, OUTPUT>,
        onResult: (OUTPUT) -> Unit = {},
        onException: (DomainException) -> Unit = {}
    ) = execute(useCase, Unit, onResult, onException)

    fun <INPUT, OUTPUT> execute(
        useCase: UseCase<INPUT, OUTPUT>,
        value: INPUT,
        onResult: (OUTPUT) -> Unit = {},
        onException: (DomainException) -> Unit = {}
    ) {
        coroutineScope.launch {
            try {
                useCase.execute(value, onResult)
            } catch (ignore: CancellationException) {
            } catch (@Suppress("TooGenericExceptionCaught") throwable: Throwable) {
                onException(
                    (throwable as? DomainException)
                        ?: UnknownDomainException(throwable)
                )
            }
        }
    }
}
