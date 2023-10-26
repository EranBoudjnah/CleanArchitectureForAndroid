package com.mitteloupe.whoami.test.test

fun retry(waitMilliseconds: Long = 200L, repeat: Int = 5, block: () -> Unit) {
    repeat(repeat) {
        try {
            block()
        } catch (ignore: IllegalArgumentException) {
            Thread.sleep(waitMilliseconds)
        }
    }
}
