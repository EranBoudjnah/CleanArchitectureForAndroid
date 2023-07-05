package com.mitteloupe.whoami.test.idlingresource

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback
import okhttp3.Dispatcher
import okhttp3.OkHttpClient

class EspressoOkHttp3IdlingResource private constructor(
    private val name: String,
    private val dispatcher: Dispatcher
) : IdlingResource {
    @Volatile
    var callback: ResourceCallback? = null

    init {
        dispatcher.idleCallback = Runnable {
            val callback = callback
            callback?.onTransitionToIdle()
        }
    }

    override fun getName(): String = name

    override fun isIdleNow(): Boolean =
        dispatcher.runningCallsCount() == 0

    override fun registerIdleTransitionCallback(callback: ResourceCallback) {
        this.callback = callback
    }

    companion object {
        fun create(name: String, client: OkHttpClient): EspressoOkHttp3IdlingResource =
            EspressoOkHttp3IdlingResource(name, client.dispatcher)
    }
}
