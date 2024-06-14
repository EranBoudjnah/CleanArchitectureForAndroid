package com.mitteloupe.whoami.test.server.response

import com.mitteloupe.whoami.test.asset.assetReader
import com.mitteloupe.whoami.test.server.ResponseDispatcher
import com.mitteloupe.whoami.test.server.ServerResponse

data class SimpleResponse(
    private val responseCode: Int = 200,
    private val bodyFileName: String = "",
    private val headers: List<Pair<String, String>> = emptyList()
) : MockResponseContents {

    private val responseBody by lazy {
        if (bodyFileName.isEmpty()) {
            ""
        } else {
            assetReader.getAssetAsString(bodyFileName)
        }
    }

    override fun mockResponse(responseDispatcher: ResponseDispatcher) = ServerResponse(
        code = responseCode,
        headers = headers,
        body = responseBody
    )
}
