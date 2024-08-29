package com.mitteloupe.whoami.test.idlingresource

import androidx.compose.ui.test.IdlingResource
import okhttp3.Dispatcher
import okhttp3.OkHttpClient

class ComposeOkHttp3IdlingResource private constructor(dispatcher: Dispatcher) : IdlingResource {
    override val isIdleNow: Boolean = dispatcher.runningCallsCount() == 0

    companion object {
        fun create(client: OkHttpClient): ComposeOkHttp3IdlingResource =
            ComposeOkHttp3IdlingResource(client.dispatcher)
    }
}
