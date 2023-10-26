package com.mitteloupe.whoami.test.test

fun doesNot(description: String, block: () -> Unit) {
    try {
        block()
        throw IllegalArgumentException("Unexpected: $description")
    } catch (ignore: IllegalArgumentException) {
    }
}
