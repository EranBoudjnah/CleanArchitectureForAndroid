package com.mitteloupe.whoami.di

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
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HomeDataModule {
    @Provides
    fun providesConnectionDetailsToDomainResolver() = ConnectionDetailsToDomainResolver()

    @Provides
    fun providesThrowableToDomainMapper() = ThrowableToDomainMapper()

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
    fun providesSaveConnectionDetailsRepository(
        ipAddressHistoryDataSource: IpAddressHistoryDataSource,
        connectionDetailsToDataMapper: ConnectionDetailsToDataMapper
    ): SaveConnectionDetailsRepository =
        ConnectionHistoryRepository(ipAddressHistoryDataSource, connectionDetailsToDataMapper)
}
