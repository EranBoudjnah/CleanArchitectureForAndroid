package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val IP_ADDRESS = "IP Address"
    const val IP_ADDRESS_INFORMATION = "IP Address Information"

    @Provides
    fun providesMoshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Provides
    @Named(IP_ADDRESS)
    fun providesIpAddressRetrofit(
        @Named(NetworkUrlModule.IP_ADDRESS) baseUrl: String,
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(moshiConverterFactory)
        .client(okHttpClient)
        .build()

    @Provides
    @Named(IP_ADDRESS_INFORMATION)
    fun providesIpAddressInformationRetrofit(
        @Named(NetworkUrlModule.IP_ADDRESS_INFORMATION) baseUrl: String,
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(moshiConverterFactory)
        .client(okHttpClient)
        .build()
}
