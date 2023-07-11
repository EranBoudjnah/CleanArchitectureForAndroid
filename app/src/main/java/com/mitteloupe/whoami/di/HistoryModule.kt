package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSource
import com.mitteloupe.whoami.history.data.mapper.SavedIpAddressRecordToDomainMapper
import com.mitteloupe.whoami.history.data.repository.ConnectionHistoryRepository
import com.mitteloupe.whoami.history.domain.repository.GetHistoryRepository
import com.mitteloupe.whoami.history.domain.usecase.GetHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HistoryModule {
    @Provides
    fun providesSavedIpAddressRecordToDomainMapper() = SavedIpAddressRecordToDomainMapper()

    @Provides
    fun providesGetHistoryRepository(
        ipAddressHistoryDataSource: IpAddressHistoryDataSource,
        savedIpAddressRecordToDomainMapper: SavedIpAddressRecordToDomainMapper
    ): GetHistoryRepository =
        ConnectionHistoryRepository(ipAddressHistoryDataSource, savedIpAddressRecordToDomainMapper)

    @Provides
    fun providesGetHistoryUseCase(
        getHistoryRepository: GetHistoryRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetHistoryUseCase(getHistoryRepository, coroutineContextProvider)
}
