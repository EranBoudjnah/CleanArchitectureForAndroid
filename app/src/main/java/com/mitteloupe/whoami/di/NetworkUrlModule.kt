package com.mitteloupe.whoami.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object NetworkUrlModule {
    internal const val IP_ADDRESS = "IP Address"
    internal const val IP_ADDRESS_INFORMATION = "IP Address Information"

    private const val IPIFY_URL = "https://api.ipify.org/"
    private const val IPINFO_URL = "https://ipinfo.io/"

    @Provides
    @Named(IP_ADDRESS)
    fun providesIpifyUrl() = IPIFY_URL

    @Provides
    @Named(IP_ADDRESS_INFORMATION)
    fun providesIpInfoUrl() = IPINFO_URL
}
