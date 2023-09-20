package com.mitteloupe.whoami.history.domain.usecase

import com.mitteloupe.whoami.architecture.domain.usecase.BackgroundExecutingUseCase
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.history.domain.model.SavedIpAddressRecordDomainModel
import com.mitteloupe.whoami.history.domain.repository.GetHistoryRepository

class GetHistoryUseCase(
    private val getHistoryRepository: GetHistoryRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<Unit, Collection<SavedIpAddressRecordDomainModel>>(
    coroutineContextProvider
) {
    override fun executeInBackground(request: Unit) =
        getHistoryRepository.history()
}
