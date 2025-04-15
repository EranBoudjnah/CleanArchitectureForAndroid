package com.mitteloupe.whoami.test.asset

import androidx.test.platform.app.InstrumentationRegistry
import java.io.InputStream

fun getAssetAsString(name: String): String =
    processAssetStream(name) { stream -> stream.bufferedReader().readText() }

fun <OUTPUT> processAssetStream(
    filename: String,
    performOnStream: (inputStream: InputStream) -> OUTPUT
): OUTPUT = InstrumentationRegistry.getInstrumentation().context.assets.open(filename)
    .use { stream -> performOnStream(stream) }
