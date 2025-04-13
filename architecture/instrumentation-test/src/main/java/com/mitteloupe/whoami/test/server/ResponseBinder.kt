package com.mitteloupe.whoami.test.server

import com.mitteloupe.whoami.test.server.response.MockResponseContents

interface ResponseBinder {
    var onWebSocketMessage: (String) -> Unit

    val usedEndpoints: Set<String>

    fun bindResponse(request: MockRequest, response: MockResponseContents)

    fun reset()
}
