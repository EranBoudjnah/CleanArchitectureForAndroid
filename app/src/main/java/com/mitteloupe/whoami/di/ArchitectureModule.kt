package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ArchitectureModule {
    @Provides
    fun providesCoroutineContextProvider(): CoroutineContextProvider =
        CoroutineContextProvider.Default

    @Provides
    fun providesUseCaseExecutor() = UseCaseExecutor()
}
