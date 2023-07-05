package com.mitteloupe.whoami.architecture.domain

import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import kotlinx.coroutines.withContext

abstract class BackgroundExecutingUseCase<REQUEST, RESULT>(
    private val coroutineContextProvider: CoroutineContextProvider
) : UseCase<REQUEST, RESULT> {
    final override suspend fun execute(
        input: REQUEST,
        onResult: (RESULT) -> Unit
    ) {
        val result = withContext(coroutineContextProvider.io) {
            executeInBackground(input)
        }
        onResult(result)
    }

    abstract fun executeInBackground(
        request: REQUEST
    ): RESULT
}
