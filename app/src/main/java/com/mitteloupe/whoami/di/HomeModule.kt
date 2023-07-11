package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.datasource.connection.datasource.ConnectionDataSource
import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSource
import com.mitteloupe.whoami.datasource.ipaddress.datasource.IpAddressDataSource
import com.mitteloupe.whoami.datasource.ipaddressinformation.datasource.IpAddressInformationDataSource
import com.mitteloupe.whoami.home.data.mapper.ConnectionDetailsToDataMapper
import com.mitteloupe.whoami.home.data.mapper.ConnectionDetailsToDomainResolver
import com.mitteloupe.whoami.home.data.mapper.ThrowableToDomainMapper
import com.mitteloupe.whoami.home.data.repository.ConnectionDetailsRepository
import com.mitteloupe.whoami.home.data.repository.ConnectionHistoryRepository
import com.mitteloupe.whoami.home.domain.repository.GetConnectionDetailsRepository
import com.mitteloupe.whoami.home.domain.repository.SaveConnectionDetailsRepository
import com.mitteloupe.whoami.home.domain.usecase.GetConnectionDetailsUseCase
import com.mitteloupe.whoami.home.domain.usecase.SaveConnectionDetailsUseCase
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionStateToPresentationMapper
import com.mitteloupe.whoami.home.presentation.mapper.ExceptionToPresentationMapper
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsToUiMapper
import com.mitteloupe.whoami.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Singleton
    @Provides
    fun providesNavigator() = Navigator()

    @Provides
    fun providesConnectionDetailsToDomainResolver() =
        ConnectionDetailsToDomainResolver()

    @Provides
    fun providesThrowableToDomainMapper() =
        ThrowableToDomainMapper()

    @Provides
    @Reusable
    fun providesConnectionDetailsRepository(
        ipAddressDataSource: IpAddressDataSource,
        ipAddressInformationDataSource: IpAddressInformationDataSource,
        connectionDataSource: ConnectionDataSource,
        connectionDetailsToDomainResolver: ConnectionDetailsToDomainResolver,
        throwableToDomainMapper: ThrowableToDomainMapper
    ) = ConnectionDetailsRepository(
        ipAddressDataSource,
        ipAddressInformationDataSource,
        connectionDataSource,
        connectionDetailsToDomainResolver,
        throwableToDomainMapper
    )

    @Provides
    fun providesGetConnectionDetailsRepository(
        connectionDetailsRepository: ConnectionDetailsRepository
    ): GetConnectionDetailsRepository = connectionDetailsRepository

    @Provides
    fun providesGetConnectionDetailsUseCase(
        getConnectionDetailsRepository: GetConnectionDetailsRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = GetConnectionDetailsUseCase(getConnectionDetailsRepository, coroutineContextProvider)

    @Provides
    fun providesSaveConnectionDetailsRepository(
        ipAddressHistoryDataSource: IpAddressHistoryDataSource,
        connectionDetailsToDataMapper: ConnectionDetailsToDataMapper
    ): SaveConnectionDetailsRepository =
        ConnectionHistoryRepository(ipAddressHistoryDataSource, connectionDetailsToDataMapper)

    @Provides
    fun providesSaveConnectionDetailsUseCase(
        saveConnectionDetailsRepository: SaveConnectionDetailsRepository,
        coroutineContextProvider: CoroutineContextProvider
    ) = SaveConnectionDetailsUseCase(saveConnectionDetailsRepository, coroutineContextProvider)

    @Provides
    fun providesExceptionToPresentationMapper() = ExceptionToPresentationMapper()

    @Provides
    fun providesConnectionStateToPresentationMapper(
        exceptionToPresentationMapper: ExceptionToPresentationMapper
    ) = ConnectionStateToPresentationMapper(exceptionToPresentationMapper)

    @Provides
    fun providesHomeViewModel(
        getConnectionDetailsUseCase: GetConnectionDetailsUseCase,
        connectionStateToPresentationMapper: ConnectionStateToPresentationMapper,
        useCaseExecutor: UseCaseExecutor,
        exceptionToPresentationMapper: ExceptionToPresentationMapper
    ) = HomeViewModel(
        getConnectionDetailsUseCase,
        connectionStateToPresentationMapper,
        exceptionToPresentationMapper,
        useCaseExecutor
    )

    @Provides
    fun providesConnectionDetailsToUiMapper() = ConnectionDetailsToUiMapper()
}
