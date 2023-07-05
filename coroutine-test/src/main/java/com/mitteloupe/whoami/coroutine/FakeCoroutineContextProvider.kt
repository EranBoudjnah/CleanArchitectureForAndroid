package com.mitteloupe.whoami.coroutine

import kotlin.coroutines.CoroutineContext

private class FakeCoroutineContextProvider(
    override val main: CoroutineContext = fakeCoroutineContext,
    override val io: CoroutineContext = fakeCoroutineContext
) : CoroutineContextProvider

val fakeCoroutineContextProvider: CoroutineContextProvider =
    FakeCoroutineContextProvider()
