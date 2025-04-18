package com.mitteloupe.whoami.test.server

import android.util.Log
import com.mitteloupe.whoami.test.server.response.MockResponseFactory
import okhttp3.Headers
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockDispatcher :
    Dispatcher(),
    ResponseBinder {
    override var testName: String = ""

    override val usedEndpoints: Set<MockRequest>
        field = mutableSetOf()

    private val responses = mutableMapOf<MockRequest, MockResponseFactory>()

    var webSocket: WebSocket? = null

    override var onWebSocketMessage: (String) -> Unit = {}

    override fun bindResponse(requestResponseFactory: MockRequestResponseFactory) {
        responses[requestResponseFactory.request] = requestResponseFactory.responseFactory
    }

    override fun reset() {
        responses.clear()
        usedEndpoints.clear()
    }

    override fun dispatch(request: RecordedRequest): MockResponse {
        val endPoint = request.path!!.substringBefore("?")
        val matchingRequest = responses.entries.firstOrNull { requestResponse ->
            requestResponse.key.url == endPoint
        }?.also { requestResponse ->
            usedEndpoints.add(requestResponse.key)
        }
        val response = matchingRequest?.value?.mockResponse() ?: MockResponse(code = 404).also {
            Log.w(TAG, "$testName: ${request.path} not stubbed!")
        }
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
            MockResponse().apply {
                headers = Headers.headersOf(*response.headers.toArray())
            }.setResponseCode(response.code)
                .setBody(response.body)
        }
    }

    private fun Collection<Pair<String, String>>.toArray(): Array<String> =
        flatMap { listOf(it.first, it.second) }.toTypedArray()

    companion object {
        const val TAG = "MockDispatcher"
    }
}
