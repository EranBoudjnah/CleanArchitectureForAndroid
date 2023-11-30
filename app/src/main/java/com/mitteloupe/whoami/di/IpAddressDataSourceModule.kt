package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.datasource.ipaddress.datasource.IpAddressDataSource
import com.mitteloupe.whoami.datasource.ipaddress.datasource.IpAddressDataSourceImpl
import com.mitteloupe.whoami.datasource.ipaddress.mapper.IpAddressToDataMapper
import com.mitteloupe.whoami.datasource.ipaddress.service.IpAddressService
import com.mitteloupe.whoami.di.NetworkModule.IP_ADDRESS
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
object IpAddressDataSourceModule {
    @Provides
    fun providesIpAddressService(@Named(IP_ADDRESS) retrofit: Retrofit): IpAddressService =
        retrofit.create()

    @Provides
    fun providesIpAddressToDataMapper() = IpAddressToDataMapper()

    @Provides
    fun providesIpAddressDataSource(
        ipAddressServiceProvider: Provider<IpAddressService>,
        ipAddressToDataMapper: IpAddressToDataMapper
    ): IpAddressDataSource =
        IpAddressDataSourceImpl(lazy { ipAddressServiceProvider.get() }, ipAddressToDataMapper)
}
