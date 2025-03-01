package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.datasource.connection.datasource.ConnectionDataSource
import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSource
import com.mitteloupe.whoami.datasource.ipaddress.datasource.IpAddressDataSource
import com.mitteloupe.whoami.datasource.ipaddressinformation.datasource.IpAddressInformationDataSource
import com.mitteloupe.whoami.home.data.mapper.ConnectionDetailsDataMapper
import com.mitteloupe.whoami.home.data.mapper.ConnectionDetailsDomainResolver
import com.mitteloupe.whoami.home.data.mapper.ThrowableDomainMapper
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
    fun providesConnectionDetailsDomainResolver() = ConnectionDetailsDomainResolver()

    @Provides
    fun providesThrowableDomainMapper() = ThrowableDomainMapper()

    @Provides
    @Reusable
    fun providesConnectionDetailsRepository(
        ipAddressDataSource: IpAddressDataSource,
        ipAddressInformationDataSource: IpAddressInformationDataSource,
        connectionDataSource: ConnectionDataSource,
        connectionDetailsDomainResolver: ConnectionDetailsDomainResolver,
        throwableDomainMapper: ThrowableDomainMapper
    ) = ConnectionDetailsRepository(
        ipAddressDataSource,
        ipAddressInformationDataSource,
        connectionDataSource,
        connectionDetailsDomainResolver,
        throwableDomainMapper
    )

    @Provides
    fun providesGetConnectionDetailsRepository(
        connectionDetailsRepository: ConnectionDetailsRepository
    ): GetConnectionDetailsRepository = connectionDetailsRepository

    @Provides
    fun providesSaveConnectionDetailsRepository(
        ipAddressHistoryDataSource: IpAddressHistoryDataSource,
        connectionDetailsDataMapper: ConnectionDetailsDataMapper
    ): SaveConnectionDetailsRepository =
        ConnectionHistoryRepository(ipAddressHistoryDataSource, connectionDetailsDataMapper)
}
