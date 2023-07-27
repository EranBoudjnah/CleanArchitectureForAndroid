package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.localstore.AppKeyValueStore
import com.mitteloupe.whoami.test.localstore.KeyValueStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalStoreModule {
    @Provides
    fun providesKeyValueStore(): KeyValueStore = AppKeyValueStore()
}
