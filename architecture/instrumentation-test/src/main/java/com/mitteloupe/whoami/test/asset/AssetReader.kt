package com.mitteloupe.whoami.test.asset

import androidx.test.platform.app.InstrumentationRegistry
import java.io.InputStream
import java.io.InputStreamReader

object AssetReader {
    fun getAssetAsString(name: String): String =
        javaClass.classLoader!!.getResourceAsStream("assets/$name").use { stream ->
            InputStreamReader(stream).use(InputStreamReader::readText)
        }
}

fun <OUTPUT> processAssetStream(
    filename: String,
    performOnStream: (inputString: InputStream) -> OUTPUT
): OUTPUT = InstrumentationRegistry.getInstrumentation().context.assets.open(filename)
    .use { stream -> performOnStream(stream) }
