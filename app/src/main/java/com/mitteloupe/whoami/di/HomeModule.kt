package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.domain.repository.GetConnectionDetailsRepository
import com.mitteloupe.whoami.home.domain.repository.SaveConnectionDetailsRepository
import com.mitteloupe.whoami.home.domain.usecase.GetConnectionDetailsUseCase
import com.mitteloupe.whoami.home.domain.usecase.SaveConnectionDetailsUseCase
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionDetailsDomainMapper
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionDetailsPresentationMapper
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
    fun providesConnectionStatePresentationMapper(
        exceptionPresentationMapper: ExceptionPresentationMapper
    ) = ConnectionDetailsPresentationMapper(exceptionPresentationMapper)

    @Provides
    fun providesSaveConnectionDetailsUseCase(
        saveConnectionDetailsRepository: SaveConnectionDetailsRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SaveConnectionDetailsUseCase(saveConnectionDetailsRepository, coroutineContextProvider)

    @Provides
    fun providesConnectionDetailsDomainMapper() = ConnectionDetailsDomainMapper()

    @Provides
    fun providesExceptionPresentationMapper() = ExceptionPresentationMapper()

    @Provides
    @Suppress("LongParameterList")
    fun providesHomeViewModel(
        getConnectionDetailsUseCase: GetConnectionDetailsUseCase,
        connectionDetailsPresentationMapper: ConnectionDetailsPresentationMapper,
        saveConnectionDetailsUseCase: SaveConnectionDetailsUseCase,
        connectionDetailsDomainMapper: ConnectionDetailsDomainMapper,
        exceptionPresentationMapper: ExceptionPresentationMapper,
        useCaseExecutor: UseCaseExecutor
    ) = HomeViewModel(
        getConnectionDetailsUseCase,
        connectionDetailsPresentationMapper,
        saveConnectionDetailsUseCase,
        connectionDetailsDomainMapper,
        exceptionPresentationMapper,
        useCaseExecutor
    )
}
