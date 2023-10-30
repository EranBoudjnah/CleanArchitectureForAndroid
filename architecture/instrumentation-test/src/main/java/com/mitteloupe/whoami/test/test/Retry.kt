package com.mitteloupe.whoami.test.test

import junit.framework.AssertionFailedError

fun retry(waitMilliseconds: Long = 200L, repeat: Int = 5, block: () -> Unit) {
    var lastException: Throwable? = null
    repeat(repeat) {
        try {
            block()
            return
        } catch (exception: AssertionFailedError) {
            lastException = exception
            Thread.sleep(waitMilliseconds)
        }
    }
    throw lastException!!
}
