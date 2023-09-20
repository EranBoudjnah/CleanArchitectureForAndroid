package com.mitteloupe.whoami.home.domain.usecase

import com.mitteloupe.whoami.architecture.domain.usecase.BackgroundExecutingUseCase
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel.Connected
import com.mitteloupe.whoami.home.domain.repository.SaveConnectionDetailsRepository

class SaveConnectionDetailsUseCase(
    private val saveConnectionDetailsRepository: SaveConnectionDetailsRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<Connected, Unit>(coroutineContextProvider) {
    override fun executeInBackground(request: Connected) {
        saveConnectionDetailsRepository.saveConnectionDetails(request)
    }
}
