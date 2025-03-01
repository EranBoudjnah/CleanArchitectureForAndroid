package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.domain.repository.GetConnectionDetailsRepository
import com.mitteloupe.whoami.home.domain.repository.SaveConnectionDetailsRepository
import com.mitteloupe.whoami.home.domain.usecase.GetConnectionDetailsUseCase
import com.mitteloupe.whoami.home.domain.usecase.SaveConnectionDetailsUseCase
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionDetailsPresentationMapper
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionDetailsToDomainMapper
import com.mitteloupe.whoami.home.presentation.mapper.ExceptionPresentationMapper
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
        exceptionPresentationMapper: ExceptionPresentationMapper
    ) = ConnectionDetailsPresentationMapper(exceptionPresentationMapper)

    @Provides
    fun providesSaveConnectionDetailsUseCase(
        saveConnectionDetailsRepository: SaveConnectionDetailsRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SaveConnectionDetailsUseCase(saveConnectionDetailsRepository, coroutineContextProvider)

    @Provides
    fun providesConnectionDetailsToDomainMapper() = ConnectionDetailsToDomainMapper()

    @Provides
    fun providesExceptionToPresentationMapper() = ExceptionPresentationMapper()

    @Provides
    fun providesHomeViewModel(
        getConnectionDetailsUseCase: GetConnectionDetailsUseCase,
        connectionDetailsPresentationMapper: ConnectionDetailsPresentationMapper,
        saveConnectionDetailsUseCase: SaveConnectionDetailsUseCase,
        connectionDetailsToDomainMapper: ConnectionDetailsToDomainMapper,
        exceptionPresentationMapper: ExceptionPresentationMapper,
        useCaseExecutor: UseCaseExecutor
    ) = HomeViewModel(
        getConnectionDetailsUseCase,
        connectionDetailsPresentationMapper,
        saveConnectionDetailsUseCase,
        connectionDetailsToDomainMapper,
        exceptionPresentationMapper,
        useCaseExecutor
    )
}
