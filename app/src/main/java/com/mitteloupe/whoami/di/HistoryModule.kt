package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.history.domain.repository.GetHistoryRepository
import com.mitteloupe.whoami.history.domain.repository.GetHistoryRepository.FakeGetHistoryRepository
import com.mitteloupe.whoami.history.domain.usecase.GetHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HistoryModule {
    @Provides
    fun providesGetHistoryRepository(): GetHistoryRepository =
        FakeGetHistoryRepository()

    @Provides
    fun providesGetHistoryUseCase(
        getHistoryRepository: GetHistoryRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetHistoryUseCase(getHistoryRepository, coroutineContextProvider)
}
