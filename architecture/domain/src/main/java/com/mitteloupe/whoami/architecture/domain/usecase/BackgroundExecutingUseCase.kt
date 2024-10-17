package com.mitteloupe.whoami.architecture.domain.usecase

import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BackgroundExecutingUseCase<REQUEST, RESULT>(
    private val coroutineContextProvider: CoroutineContextProvider,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) : UseCase<REQUEST, RESULT> {
    final override fun execute(input: REQUEST, onResult: (RESULT) -> Unit) {
        try {
            coroutineScope.launch {
                val result = withContext(coroutineContextProvider.io) {
                    executeInBackground(input)
                }
                onResult(result)
            }
        } catch (_: CancellationException) {
        }
    }

    abstract fun executeInBackground(request: REQUEST): RESULT
}
