package com.mitteloupe.whoami.test.asset

import androidx.test.platform.app.InstrumentationRegistry
import java.io.InputStream
import java.io.InputStreamReader

object AssetReader {
    fun getAssetAsString(name: String): String =
        javaClass.classLoader!!.getResourceAsStream("assets/$name").use { stream ->
            InputStreamReader(stream).use { reader ->
                reader.readText()
            }
        }
}

fun <OUTPUT> processAssetStream(
    filename: String,
    performOnStream: (inputString: InputStream) -> OUTPUT
): OUTPUT {
    val stream = InstrumentationRegistry.getInstrumentation().context.assets.open(filename)
    val result = performOnStream(stream)
    stream.close()
    return result
}
