package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.domain.repository.GetConnectionDetailsRepository
import com.mitteloupe.whoami.home.domain.repository.SaveConnectionDetailsRepository
import com.mitteloupe.whoami.home.domain.usecase.GetConnectionDetailsUseCase
import com.mitteloupe.whoami.home.domain.usecase.SaveConnectionDetailsUseCase
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionDetailsToDomainMapper
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionStateToPresentationMapper
import com.mitteloupe.whoami.home.presentation.mapper.ExceptionToPresentationMapper
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Provides
    fun providesGetConnectionDetailsUseCase(
        getConnectionDetailsRepository: GetConnectionDetailsRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetConnectionDetailsUseCase(getConnectionDetailsRepository, coroutineContextProvider)

    @Provides
    fun providesConnectionStateToPresentationMapper(
        exceptionToPresentationMapper: ExceptionToPresentationMapper
    ) = ConnectionStateToPresentationMapper(exceptionToPresentationMapper)

    @Provides
    fun providesSaveConnectionDetailsUseCase(
        saveConnectionDetailsRepository: SaveConnectionDetailsRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SaveConnectionDetailsUseCase(saveConnectionDetailsRepository, coroutineContextProvider)

    @Provides
    fun providesConnectionDetailsToDomainMapper() = ConnectionDetailsToDomainMapper()

    @Provides
    fun providesExceptionToPresentationMapper() = ExceptionToPresentationMapper()

    @Provides
    fun providesHomeViewModel(
        getConnectionDetailsUseCase: GetConnectionDetailsUseCase,
        connectionStateToPresentationMapper: ConnectionStateToPresentationMapper,
        saveConnectionDetailsUseCase: SaveConnectionDetailsUseCase,
        connectionDetailsToDomainMapper: ConnectionDetailsToDomainMapper,
        exceptionToPresentationMapper: ExceptionToPresentationMapper,
        useCaseExecutor: UseCaseExecutor
    ) = HomeViewModel(
        getConnectionDetailsUseCase,
        connectionStateToPresentationMapper,
        saveConnectionDetailsUseCase,
        connectionDetailsToDomainMapper,
        exceptionToPresentationMapper,
        useCaseExecutor
    )
}
