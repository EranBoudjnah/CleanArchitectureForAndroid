package com.mitteloupe.whoami.history.domain.usecase

import com.mitteloupe.whoami.architecture.domain.usecase.ContinuousExecutingUseCase
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.history.domain.model.SavedIpAddressRecordDomainModel
import com.mitteloupe.whoami.history.domain.repository.GetHistoryRepository

class GetHistoryUseCase(
    private val getHistoryRepository: GetHistoryRepository,
    coroutineContextProvider: CoroutineContextProvider
) : ContinuousExecutingUseCase<Unit, Collection<SavedIpAddressRecordDomainModel>>(
    coroutineContextProvider
) {
    override fun executeInBackground(request: Unit) = getHistoryRepository.history()
}
