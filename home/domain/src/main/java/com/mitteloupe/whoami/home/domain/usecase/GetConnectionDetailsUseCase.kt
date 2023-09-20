package com.mitteloupe.whoami.home.domain.usecase

import com.mitteloupe.whoami.architecture.domain.usecase.ContinuousExecutingUseCase
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel
import com.mitteloupe.whoami.home.domain.repository.GetConnectionDetailsRepository

class GetConnectionDetailsUseCase(
    private val getConnectionDetailsRepository: GetConnectionDetailsRepository,
    coroutineContextProvider: CoroutineContextProvider
) : ContinuousExecutingUseCase<Unit, ConnectionDetailsDomainModel>(coroutineContextProvider) {
    override fun executeInBackground(request: Unit) =
        getConnectionDetailsRepository.connectionDetails()
}
