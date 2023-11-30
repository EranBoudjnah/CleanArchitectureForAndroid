package com.mitteloupe.whoami.test.server

private typealias MockRequestResponsePairList = List<Pair<String, MockRequestResponse>>
private typealias MockRequestResponseMap = Map<String, MockRequestResponse>

abstract class ResponseStore {
    val responses by lazy {
        internalResponses.toValidatedMap()
    }

    protected abstract val internalResponses: List<Pair<String, MockRequestResponse>>

    private fun MockRequestResponsePairList.toValidatedMap(): MockRequestResponseMap {
        val responses = toMap()
        check(responses.size == size) {
            "Duplicate Request/Response key declared. " +
                "Make sure all Request/Response keys are unique."
        }
        return responses
    }
}
