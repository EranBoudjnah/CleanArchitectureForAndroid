package com.mitteloupe.whoami.di

import android.content.Context
import android.net.ConnectivityManager
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.datasource.connection.datasource.ConnectionDataSource
import com.mitteloupe.whoami.datasource.connection.datasource.ConnectionDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.MainScope

@Module
@InstallIn(SingletonComponent::class)
object ConnectionDataSourceModule {
    @Provides
    fun providesConnectivityManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    fun providesConnectionDataSource(
        connectivityManager: ConnectivityManager,
        coroutineContextProvider: CoroutineContextProvider
    ): ConnectionDataSource =
        ConnectionDataSourceImpl(connectivityManager, MainScope(), coroutineContextProvider)
}
