package com.mitteloupe.whoami.test.server

interface ResponseBinder {
    fun bindResponse(requestResponseFactory: MockRequestResponseFactory)

    val usedEndpoints: Set<String>

    fun reset()

    var onWebSocketMessage: (String) -> Unit
}
