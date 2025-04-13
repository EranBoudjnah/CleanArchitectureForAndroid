package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.server.AppResponseStore
import com.mitteloupe.whoami.test.server.MockBinder
import com.mitteloupe.whoami.test.server.MockWebServerProvider
import com.mitteloupe.whoami.test.server.ResponseStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.mockwebserver.Dispatcher

@Module
@InstallIn(SingletonComponent::class)
object NetworkTestModule {
    @Provides
    @Singleton
    fun providesMockDispatcher() = MockBinder()

    @Provides
    fun providesDispatcher(mockDispatcher: MockBinder): Dispatcher = mockDispatcher

    @Provides
    fun providesResponseStore(): ResponseStore = AppResponseStore()

    @Provides
    @Singleton
    fun providesMockWebServerProvider() = MockWebServerProvider()

    @Provides
    @Singleton
    fun providesMockWebServer(
        mockWebServerProvider: MockWebServerProvider,
        dispatcher: Dispatcher
    ) = mockWebServerProvider.mockWebServer(dispatcher)
}
