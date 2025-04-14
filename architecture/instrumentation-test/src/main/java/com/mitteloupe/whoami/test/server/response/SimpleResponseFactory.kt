package com.mitteloupe.whoami.test.server.response

import com.mitteloupe.whoami.test.asset.assetReader
import com.mitteloupe.whoami.test.server.MockResponse

data class SimpleResponseFactory(
    private val code: Int = 200,
    private val headers: List<Pair<String, String>> = emptyList(),
    private val bodyFileName: String = ""
) : MockResponseFactory {
    private val body by lazy {
        if (bodyFileName.isEmpty()) {
            ""
        } else {
            assetReader.getAssetAsString(bodyFileName)
        }
    }

    override fun mockResponse() = MockResponse(code = code, headers = headers, body = body)
}
