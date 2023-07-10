package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSource
import com.mitteloupe.whoami.home.data.mapper.ConnectionDetailsToDataMapper
import com.mitteloupe.whoami.home.data.repository.ConnectionHistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ConnectionHistoryModule {
    @Provides
    fun providesConnectionDetailsToDataMapper() = ConnectionDetailsToDataMapper()

    @Provides
    fun providesConnectionHistoryRepository(
        ipAddressHistoryDataSource: IpAddressHistoryDataSource,
        connectionDetailsToDataMapper: ConnectionDetailsToDataMapper
    ) = ConnectionHistoryRepository(
        ipAddressHistoryDataSource,
        connectionDetailsToDataMapper
    )
}
