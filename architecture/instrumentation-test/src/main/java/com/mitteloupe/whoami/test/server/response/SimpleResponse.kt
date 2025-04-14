package com.mitteloupe.whoami.test.server.response

import com.mitteloupe.whoami.test.asset.assetReader
import com.mitteloupe.whoami.test.server.MockResponse

data class SimpleResponse(
    private val code: Int = 200,
    private val bodyFileName: String = "",
    private val headers: List<Pair<String, String>> = emptyList()
) : MockResponseContents {
    private val body by lazy {
        if (bodyFileName.isEmpty()) {
            ""
        } else {
            assetReader.getAssetAsString(bodyFileName)
        }
    }

    override fun mockResponse() = MockResponse(code = code, headers = headers, body = body)
}
