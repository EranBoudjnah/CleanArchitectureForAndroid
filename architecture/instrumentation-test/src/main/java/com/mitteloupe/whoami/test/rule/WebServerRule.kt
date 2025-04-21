package com.mitteloupe.whoami.test.rule

import com.mitteloupe.whoami.test.annotation.ServerRequestResponse
import com.mitteloupe.whoami.test.server.MockRequest
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
        WebServerStatement(lazyMockDispatcher, lazyResponseStore, base, description)
}

private class WebServerStatement(
    private val lazyMockDispatcher: Lazy<ResponseBinder>,
    private val lazyResponseStore: Lazy<ResponseStore>,
    private val base: Statement,
    private val description: Description
) : Statement() {
    val responseStore by lazy { lazyResponseStore.value }
    val mockDispatcher by lazy { lazyMockDispatcher.value }

    override fun evaluate() {
        mockDispatcher.testName = description.displayName
        val stubbedResponseKeys = bindRequestResponseFactories()
        base.evaluate()
        assertAllStubsUsed(stubbedResponseKeys)
        mockDispatcher.reset()
    }

    private fun bindRequestResponseFactories(): Set<MockRequest> {
        val requestResponses = description.requestResponseIds()
            .map { requestResponseId ->
                requireNotNull(
                    responseStore.responseFactories[requestResponseId]
                ) { "Request/Response ID $requestResponseId not found." }
            }

        requestResponses.forEach { requestResponse ->
            mockDispatcher.bindResponse(requestResponse)
        }
        val stubbedResponseKeys = requestResponses
            .map { requestResponse -> requestResponse.request }
            .toSet()
        return stubbedResponseKeys
    }

    private fun assertAllStubsUsed(stubbedResponseKeys: Set<MockRequest>) {
        val usedResponseKeys = mockDispatcher.usedEndpoints.toSet()

        val unusedResponseKeys = stubbedResponseKeys - usedResponseKeys
        check(unusedResponseKeys.isEmpty()) {
            "${unusedResponseKeys.size} unused stubbed URLs:\n[" +
                unusedResponseKeys.joinToString("]\n[") + "]"
        }
    }

    private fun Description.requestResponseIds() =
        annotations.filterIsInstance<ServerRequestResponse>()
            .flatMap { serverResponse -> serverResponse.requestResponseIds.toList() }
}
