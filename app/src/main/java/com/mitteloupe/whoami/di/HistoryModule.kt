package com.mitteloupe.whoami.di

import android.content.res.Resources
import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSource
import com.mitteloupe.whoami.history.data.mapper.HistoryRecordDeletionToDataMapper
import com.mitteloupe.whoami.history.data.mapper.SavedIpAddressRecordToDomainMapper
import com.mitteloupe.whoami.history.data.repository.ConnectionHistoryRepository
import com.mitteloupe.whoami.history.domain.repository.DeleteHistoryRecordRepository
import com.mitteloupe.whoami.history.domain.repository.GetHistoryRepository
import com.mitteloupe.whoami.history.domain.usecase.DeleteHistoryRecordUseCase
import com.mitteloupe.whoami.history.domain.usecase.GetHistoryUseCase
import com.mitteloupe.whoami.history.presentation.mapper.DeleteHistoryRecordRequestToDomainMapper
import com.mitteloupe.whoami.history.presentation.mapper.SavedIpAddressRecordToPresentationMapper
import com.mitteloupe.whoami.history.presentation.viewmodel.HistoryViewModel
import com.mitteloupe.whoami.history.ui.mapper.HistoryRecordToUiMapper
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
    fun providesHistoryRecordDeletionToDataMapper() = HistoryRecordDeletionToDataMapper()

    @Provides
    fun providesConnectionHistoryRepository(
        ipAddressHistoryDataSource: IpAddressHistoryDataSource,
        savedIpAddressRecordToDomainMapper: SavedIpAddressRecordToDomainMapper,
        recordDeletionToDataMapper: HistoryRecordDeletionToDataMapper
    ) = ConnectionHistoryRepository(
        ipAddressHistoryDataSource,
        savedIpAddressRecordToDomainMapper,
        recordDeletionToDataMapper
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
    fun providesSavedIpAddressRecordToPresentationMapper() =
        SavedIpAddressRecordToPresentationMapper()

    @Provides
    fun providesDeleteHistoryRecordUseCase(
        deleteHistoryRecordRepository: DeleteHistoryRecordRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = DeleteHistoryRecordUseCase(deleteHistoryRecordRepository, coroutineContextProvider)

    @Provides
    fun providesDeleteHistoryRecordRequestToDomainMapper() =
        DeleteHistoryRecordRequestToDomainMapper()

    @Provides
    fun providesHistoryViewModel(
        getHistoryUseCase: GetHistoryUseCase,
        savedIpAddressRecordToPresentationMapper: SavedIpAddressRecordToPresentationMapper,
        deleteHistoryRecordUseCase: DeleteHistoryRecordUseCase,
        deleteHistoryRecordRequestToDomainMapper: DeleteHistoryRecordRequestToDomainMapper,
        useCaseExecutor: UseCaseExecutor
    ) = HistoryViewModel(
        getHistoryUseCase,
        savedIpAddressRecordToPresentationMapper,
        deleteHistoryRecordUseCase,
        deleteHistoryRecordRequestToDomainMapper,
        useCaseExecutor
    )

    @Provides
    fun providesHistoryRecordToUiMapper(
        resources: Resources
    ) = HistoryRecordToUiMapper(resources)
}
