package com.mitteloupe.whoami.test.test

fun retry(waitMilliseconds: Long = 200L, repeat: Int = 5, block: () -> Unit) {
    var lastException: IllegalArgumentException? = null
    repeat(repeat) {
        try {
            block()
            return
        } catch (exception: IllegalArgumentException) {
            lastException = exception
            Thread.sleep(waitMilliseconds)
        }
    }
    throw lastException!!
}
