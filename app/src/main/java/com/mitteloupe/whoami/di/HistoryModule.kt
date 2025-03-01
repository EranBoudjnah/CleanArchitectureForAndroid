package com.mitteloupe.whoami.di

import android.content.res.Resources
import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSource
import com.mitteloupe.whoami.history.data.mapper.HistoryRecordDeletionDataMapper
import com.mitteloupe.whoami.history.data.mapper.SavedIpAddressRecordDomainMapper
import com.mitteloupe.whoami.history.data.repository.ConnectionHistoryRepository
import com.mitteloupe.whoami.history.domain.repository.DeleteHistoryRecordRepository
import com.mitteloupe.whoami.history.domain.repository.GetHistoryRepository
import com.mitteloupe.whoami.history.domain.usecase.DeleteHistoryRecordUseCase
import com.mitteloupe.whoami.history.domain.usecase.GetHistoryUseCase
import com.mitteloupe.whoami.history.presentation.mapper.DeleteHistoryRecordRequestDomainMapper
import com.mitteloupe.whoami.history.presentation.mapper.SavedIpAddressRecordPresentationMapper
import com.mitteloupe.whoami.history.presentation.viewmodel.HistoryViewModel
import com.mitteloupe.whoami.history.ui.mapper.HistoryRecordUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HistoryModule {
    @Provides
    fun providesSavedIpAddressRecordDomainMapper() = SavedIpAddressRecordDomainMapper()

    @Provides
    fun providesHistoryRecordDeletionDataMapper() = HistoryRecordDeletionDataMapper()

    @Provides
    fun providesConnectionHistoryRepository(
        ipAddressHistoryDataSource: IpAddressHistoryDataSource,
        savedIpAddressRecordDomainMapper: SavedIpAddressRecordDomainMapper,
        historyRecordDeletionDataMapper: HistoryRecordDeletionDataMapper
    ) = ConnectionHistoryRepository(
        ipAddressHistoryDataSource,
        savedIpAddressRecordDomainMapper,
        historyRecordDeletionDataMapper
    )

    @Provides
    fun providesGetHistoryRepository(
        connectionHistoryRepository: ConnectionHistoryRepository
    ): GetHistoryRepository = connectionHistoryRepository

    @Provides
    fun providesDeleteHistoryRecordRepository(
        connectionHistoryRepository: ConnectionHistoryRepository
    ): DeleteHistoryRecordRepository = connectionHistoryRepository

    @Provides
    fun providesGetHistoryUseCase(
        getHistoryRepository: GetHistoryRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetHistoryUseCase(getHistoryRepository, coroutineContextProvider)

    @Provides
    fun providesSavedIpAddressRecordPresentationMapper() = SavedIpAddressRecordPresentationMapper()

    @Provides
    fun providesDeleteHistoryRecordUseCase(
        deleteHistoryRecordRepository: DeleteHistoryRecordRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = DeleteHistoryRecordUseCase(deleteHistoryRecordRepository, coroutineContextProvider)

    @Provides
    fun providesDeleteHistoryRecordRequestDomainMapper() = DeleteHistoryRecordRequestDomainMapper()

    @Provides
    fun providesHistoryViewModel(
        getHistoryUseCase: GetHistoryUseCase,
        savedIpAddressRecordPresentationMapper: SavedIpAddressRecordPresentationMapper,
        deleteHistoryRecordUseCase: DeleteHistoryRecordUseCase,
        deleteHistoryRecordRequestDomainMapper: DeleteHistoryRecordRequestDomainMapper,
        useCaseExecutor: UseCaseExecutor
    ) = HistoryViewModel(
        getHistoryUseCase,
        savedIpAddressRecordPresentationMapper,
        deleteHistoryRecordUseCase,
        deleteHistoryRecordRequestDomainMapper,
        useCaseExecutor
    )

    @Provides
    fun providesHistoryRecordUiMapper(resources: Resources) = HistoryRecordUiMapper(resources)
}
