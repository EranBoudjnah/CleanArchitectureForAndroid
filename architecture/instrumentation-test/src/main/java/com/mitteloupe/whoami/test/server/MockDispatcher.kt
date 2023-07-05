package com.mitteloupe.whoami.test.server

import com.mitteloupe.whoami.test.server.response.MockResponseContents
import okhttp3.Headers
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockDispatcher : Dispatcher(), ResponseDispatcher {
    private val _usedResponseKeys = mutableSetOf<String>()
    override val usedResponseKeys: Set<String> = _usedResponseKeys

    private val responses = mutableMapOf<String, MockResponseContents>()

    var webSocket: WebSocket? = null

    override var onWebSocketMessage: (String) -> Unit = {}

    override fun reset() {
        responses.clear()
        _usedResponseKeys.clear()
    }

    override fun addResponse(request: MockRequest, response: MockResponseContents) {
        responses[request.url] = response
    }

    override fun dispatch(request: RecordedRequest): MockResponse {
        val endPoint = request.path!!.substringBefore("?")
        _usedResponseKeys.add(endPoint)
        val response = responses[endPoint]?.mockResponse(this) ?: ServerResponse(code = 404)
        return if (response.upgradeToWebSocket) {
            MockResponse().withWebSocketUpgrade(
                object : WebSocketListener() {
                    override fun onOpen(webSocket: WebSocket, response: Response) {
                        this@MockDispatcher.webSocket = webSocket
                    }

                    override fun onMessage(webSocket: WebSocket, text: String) {
                        this@MockDispatcher.onWebSocketMessage(text)
                    }

                    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                        this@MockDispatcher.webSocket = null
                    }
                }
            )
        } else {
            return MockResponse().apply {
                headers = Headers.headersOf(*response.headers.toArray())
            }.setResponseCode(response.code)
                .setBody(response.body)
        }
    }

    private fun Collection<Pair<String, String>>.toArray(): Array<String> =
        flatMap { listOf(it.first, it.second) }.toTypedArray()
}
