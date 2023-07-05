package com.mitteloupe.whoami.test.server

private typealias MockRequestResponsePairList = List<Pair<String, MockRequestResponse>>
private typealias MockRequestResponseMap = Map<String, MockRequestResponse>

abstract class ResponseStore {
    val responses by lazy {
        _responses.toValidatedMap()
    }

    @Suppress("PropertyName")
    protected abstract val _responses: List<Pair<String, MockRequestResponse>>

    private fun MockRequestResponsePairList.toValidatedMap(): MockRequestResponseMap {
        val responses = toMap()
        if (responses.size != size) {
            throw IllegalStateException(
                "Duplicate Request/Response key declared. " +
                    "Make sure all Request/Response keys are unique."
            )
        }
        return responses
    }
}
