package com.mitteloupe.whoami.test.rule

import com.mitteloupe.whoami.test.annotation.ServerRequestResponse
import com.mitteloupe.whoami.test.server.ResponseBinder
import com.mitteloupe.whoami.test.server.ResponseStore
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class WebServerRule(
    private val lazyMockDispatcher: Lazy<ResponseBinder>,
    private val lazyResponseStore: Lazy<ResponseStore>
) : TestRule {
    override fun apply(base: Statement, description: Description): Statement =
        WebServerInitializationStatement(
            lazyMockDispatcher,
            lazyResponseStore,
            base,
            description
        )

    private class WebServerInitializationStatement(
        private val lazyMockDispatcher: Lazy<ResponseBinder>,
        private val lazyResponseStore: Lazy<ResponseStore>,
        private val base: Statement,
        private val description: Description
    ) : Statement() {
        val responseStore by lazy { lazyResponseStore.value }
        val mockDispatcher by lazy { lazyMockDispatcher.value }

        override fun evaluate() {
            val requestResponses = description.requestResponseIds()
                .map { requestResponseId ->
                    responseStore.responseFactories[requestResponseId]
                        ?: throw IllegalArgumentException(
                            "Request/Response ID $requestResponseId not found."
                        )
                }

            requestResponses.forEach { requestResponse ->
                mockDispatcher.bindResponse(requestResponse)
            }
            val stubbedResponseKeys = requestResponses
                .map { requestResponse -> requestResponse.request.url }
                .toSet()

            base.evaluate()

            val usedResponseKeys = mockDispatcher.usedEndpoints.toSet()

            val unusedResponseKeys = stubbedResponseKeys - usedResponseKeys
            check(unusedResponseKeys.isEmpty()) {
                "${unusedResponseKeys.size} unused stubbed URLs:\n[" +
                    unusedResponseKeys.joinToString("]\n[") + "]"
            }

            mockDispatcher.reset()
        }

        private fun Description.requestResponseIds() =
            annotations.filterIsInstance<ServerRequestResponse>()
                .flatMap { serverResponse -> serverResponse.requestResponseIds.toList() }
    }
}
