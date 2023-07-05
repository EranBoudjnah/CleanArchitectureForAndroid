package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.test.idlingresource.ComposeOkHttp3IdlingResource
import com.mitteloupe.whoami.test.idlingresource.EspressoOkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object IdlingResourceModule {
    @Provides
    fun providesEspressoOkHttpClientIdlingResource(httpClient: OkHttpClient) =
        EspressoOkHttp3IdlingResource.create("OkHttp", httpClient)

    @Provides
    fun providesEspressoIdlingResources(
        okHttp3IdlingResource: EspressoOkHttp3IdlingResource
    ): Collection<androidx.test.espresso.IdlingResource> = setOf(
        okHttp3IdlingResource
    )

    @Provides
    fun providesComposeOkHttpClientIdlingResource(httpClient: OkHttpClient) =
        ComposeOkHttp3IdlingResource.create(httpClient)

    @Provides
    fun providesIdlingResources(
        okHttp3IdlingResource: ComposeOkHttp3IdlingResource
    ): Collection<androidx.compose.ui.test.IdlingResource> = setOf(
        okHttp3IdlingResource
    )
}
