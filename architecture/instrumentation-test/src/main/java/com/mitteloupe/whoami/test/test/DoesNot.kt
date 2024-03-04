package com.mitteloupe.whoami.test.test

import junit.framework.AssertionFailedError

fun doesNot(description: String, block: () -> Unit) {
    try {
        block()
        throw IllegalStateException("Unexpected: $description")
    } catch (ignore: AssertionFailedError) {
    }
}
