package com.mitteloupe.whoami.history.domain.usecase

import com.mitteloupe.whoami.architecture.domain.usecase.BackgroundExecutingUseCase
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.history.domain.model.HistoryRecordDeletionDomainModel
import com.mitteloupe.whoami.history.domain.repository.DeleteHistoryRecordRepository

class DeleteHistoryRecordUseCase(
    private val deleteHistoryRecordRepository: DeleteHistoryRecordRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<HistoryRecordDeletionDomainModel, Unit>(
    coroutineContextProvider
) {
    override fun executeInBackground(request: HistoryRecordDeletionDomainModel) {
        deleteHistoryRecordRepository.delete(request)
    }
}
