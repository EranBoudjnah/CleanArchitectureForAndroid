package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.architecture.ui.binder.ViewStateBinder
import com.mitteloupe.whoami.architecture.ui.view.ViewsProvider
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSource
import com.mitteloupe.whoami.history.data.mapper.SavedIpAddressRecordToDomainMapper
import com.mitteloupe.whoami.history.data.repository.ConnectionHistoryRepository
import com.mitteloupe.whoami.history.domain.repository.GetHistoryRepository
import com.mitteloupe.whoami.history.domain.usecase.GetHistoryUseCase
import com.mitteloupe.whoami.history.presentation.mapper.SavedIpAddressRecordToPresentationMapper
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState
import com.mitteloupe.whoami.history.presentation.viewmodel.HistoryViewModel
import com.mitteloupe.whoami.history.ui.binder.HistoryViewStateBinder
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

    @Provides
    fun providesSavedIpAddressRecordToPresentationMapper() =
        SavedIpAddressRecordToPresentationMapper()

    @Provides
    fun providesHistoryViewModel(
        getHistoryUseCase: GetHistoryUseCase,
        savedIpAddressRecordToPresentationMapper: SavedIpAddressRecordToPresentationMapper,
        useCaseExecutor: UseCaseExecutor
    ) = HistoryViewModel(
        getHistoryUseCase,
        savedIpAddressRecordToPresentationMapper,
        useCaseExecutor
    )

    @Suppress("UNCHECKED_CAST")
    @Provides
    fun providesHistoryViewStateBinder() =
        HistoryViewStateBinder() as ViewStateBinder<HistoryViewState, ViewsProvider>
}
