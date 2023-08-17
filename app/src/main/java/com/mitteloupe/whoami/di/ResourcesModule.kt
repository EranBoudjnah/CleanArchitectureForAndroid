package com.mitteloupe.whoami.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ResourcesModule {
    @Provides
    fun providesResources(@ApplicationContext context: Context) = context.resources
}
