package com.mitteloupe.whoami.coroutine

import kotlin.coroutines.CoroutineContext

private class FakeCoroutineContext : CoroutineContext {
    override fun <R> fold(initial: R, operation: (R, CoroutineContext.Element) -> R): R = initial

    override fun <E : CoroutineContext.Element> get(key: CoroutineContext.Key<E>): E? = null

    override fun minusKey(key: CoroutineContext.Key<*>) = this
}

val fakeCoroutineContext: CoroutineContext = FakeCoroutineContext()
