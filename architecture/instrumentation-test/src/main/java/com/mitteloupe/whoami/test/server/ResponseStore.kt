package com.mitteloupe.whoami.test.server

private typealias MockRequestResponsePairList = List<Pair<String, MockRequestResponseFactory>>
private typealias MockRequestResponseMap = Map<String, MockRequestResponseFactory>

abstract class ResponseStore {
    val responseFactories by lazy {
        internalResponseFactories.toValidatedMap()
    }

    protected abstract val internalResponseFactories: MockRequestResponsePairList

    private fun MockRequestResponsePairList.toValidatedMap(): MockRequestResponseMap {
        val responses = toMap()
        check(responses.size == size) {
            "Duplicate Request/Response key declared. " +
                "Make sure all Request/Response keys are unique."
        }
        return responses
    }
}
