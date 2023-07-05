package com.mitteloupe.whoami.test.server

import com.mitteloupe.whoami.test.server.response.MockResponseContents

interface ResponseDispatcher {
    var onWebSocketMessage: (String) -> Unit

    fun reset()

    fun addResponse(request: MockRequest, response: MockResponseContents)

    val usedResponseKeys: Set<String>
}
