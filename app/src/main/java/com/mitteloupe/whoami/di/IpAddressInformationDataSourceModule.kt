package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.datasource.ipaddressinformation.datasource.IpAddressInformationDataSource
import com.mitteloupe.whoami.datasource.ipaddressinformation.datasource.IpAddressInformationDataSourceImpl
import com.mitteloupe.whoami.datasource.ipaddressinformation.mapper.IpAddressInformationDataMapper
import com.mitteloupe.whoami.datasource.ipaddressinformation.service.IpAddressInformationService
import com.mitteloupe.whoami.di.NetworkModule.IP_ADDRESS_INFORMATION
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Provider
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object IpAddressInformationDataSourceModule {
    @Provides
    fun providesIpAddressInformationService(
        @Named(IP_ADDRESS_INFORMATION) retrofit: Retrofit
    ): IpAddressInformationService = retrofit.create()

    @Provides
    fun providesIpAddressInformationDataMapper() = IpAddressInformationDataMapper()

    @Provides
    fun providesIpAddressInformationDataSource(
        ipAddressInformationServiceProvider: Provider<IpAddressInformationService>,
        ipAddressInformationDataMapper: IpAddressInformationDataMapper
    ): IpAddressInformationDataSource = IpAddressInformationDataSourceImpl(
        lazy { ipAddressInformationServiceProvider.get() },
        ipAddressInformationDataMapper
    )
}
