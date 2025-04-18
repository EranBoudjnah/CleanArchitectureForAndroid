package com.mitteloupe.whoami.test.server

interface ResponseBinder {
    var testName: String

    fun bindResponse(requestResponseFactory: MockRequestResponseFactory)

    val usedEndpoints: Set<MockRequest>

    fun reset()

    var onWebSocketMessage: (String) -> Unit
}
