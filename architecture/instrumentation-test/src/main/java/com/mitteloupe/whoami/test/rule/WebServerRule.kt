package com.mitteloupe.whoami.test.rule

import com.mitteloupe.whoami.test.annotation.ServerRequestResponse
import com.mitteloupe.whoami.test.server.ResponseDispatcher
import com.mitteloupe.whoami.test.server.ResponseStore
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class WebServerRule(
    private val lazyMockDispatchers: Lazy<Collection<ResponseDispatcher>>,
    private val lazyResponseStore: Lazy<ResponseStore>,
    private val mockWebServerUrlProvider: () -> String = { "" }
) : TestRule {
    override fun apply(
        base: Statement,
        description: Description
    ): Statement = WebServerInitializationStatement(
        lazyMockDispatchers,
        lazyResponseStore,
        mockWebServerUrlProvider,
        base,
        description
    )

    private class WebServerInitializationStatement(
        private val lazyMockDispatchers: Lazy<Collection<ResponseDispatcher>>,
        private val lazyResponseStore: Lazy<ResponseStore>,
        private val mockWebServerUrlProvider: () -> String = { "" },
        private val base: Statement,
        private val description: Description
    ) : Statement() {
        val responseStore by lazy { lazyResponseStore.value }
        val mockDispatchers by lazy { lazyMockDispatchers.value }

        override fun evaluate() {
            val requestResponses = description.requestResponseIds()
                .map { requestResponseId ->
                    responseStore.responses[requestResponseId]
                        ?: throw IllegalArgumentException(
                            "Request/Response ID $requestResponseId not found."
                        )
                }

            mockDispatchers.forEach { dispatcher ->
                requestResponses.forEach { requestResponse ->
                    dispatcher.addResponse(requestResponse.request, requestResponse.response)
                }
            }
            val stubbedResponseKeys = requestResponses.map { requestResponse ->
                requestResponse.request.url
            }.toSet()

            base.evaluate()

            val usedResponseKeys = mockDispatchers.flatMap { dispatcher ->
                dispatcher.usedResponseKeys
            }.toSet()

            val unusedResponseKeys = stubbedResponseKeys - usedResponseKeys
            check(unusedResponseKeys.isEmpty()) {
                "${unusedResponseKeys.size} unused stubbed URLs:\n[" +
                    unusedResponseKeys.joinToString("]\n[") + "]"
            }

            mockDispatchers.forEach(ResponseDispatcher::reset)
        }

        private fun Description.requestResponseIds() =
            annotations.filterIsInstance<ServerRequestResponse>()
                .flatMap { serverResponse -> serverResponse.requestResponseIds.toList() }
    }
}
