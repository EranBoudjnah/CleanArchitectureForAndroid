package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSource
import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSourceImpl
import com.mitteloupe.whoami.datasource.history.mapper.NewIpAddressRecordToSavedMapper
import com.mitteloupe.whoami.time.NowProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IpAddressHistoryDataSourceModule {
    @Provides
    fun providesNewIpAddressRecordToSavedMapper(
        nowProvider: NowProvider
    ) = NewIpAddressRecordToSavedMapper(nowProvider)

    @Provides
    @Singleton
    fun providesIpAddressHistoryDataSource(
        newIpAddressRecordToSavedMapper: NewIpAddressRecordToSavedMapper
    ): IpAddressHistoryDataSource = IpAddressHistoryDataSourceImpl(newIpAddressRecordToSavedMapper)
}
