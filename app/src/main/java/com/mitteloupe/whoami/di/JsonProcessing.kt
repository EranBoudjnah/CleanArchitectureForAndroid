package com.mitteloupe.whoami.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object JsonProcessing {
    @Provides
    fun providesKotlinJsonAdapterFactory() = KotlinJsonAdapterFactory()

    @Provides
    fun providesMoshi(kotlinJsonAdapterFactory: KotlinJsonAdapterFactory): Moshi = Moshi.Builder()
        .addLast(kotlinJsonAdapterFactory)
        .build()
}
