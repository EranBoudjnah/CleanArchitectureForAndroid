package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.server.IPIFY_ENDPOINT
import com.mitteloupe.whoami.server.IPINFO_ENDPOINT
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named
import okhttp3.mockwebserver.MockWebServer

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [
        NetworkUrlModule::class
    ]
)
object NetworkUrlTestModule {
    @Provides
    @Named(NetworkUrlModule.IP_ADDRESS)
    fun providesIpifyUrl(mockWebServer: MockWebServer) =
        mockWebServer.url(IPIFY_ENDPOINT).toString()

    @Provides
    @Named(NetworkUrlModule.IP_ADDRESS_INFORMATION)
    fun providesIpInfoUrl(mockWebServer: MockWebServer) =
        mockWebServer.url(IPINFO_ENDPOINT).toString()
}
