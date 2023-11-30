package com.mitteloupe.whoami.test.localstore

private typealias KeyValuePairList = List<Pair<String, Pair<String, Any>>>
private typealias KeyValueMap = Map<String, Pair<String, Any>>

abstract class KeyValueStore {
    val keyValues by lazy {
        internalKeyValues.toValidatedMap()
    }

    protected abstract val internalKeyValues: List<Pair<String, Pair<String, Any>>>

    private fun KeyValuePairList.toValidatedMap(): KeyValueMap {
        val responses = toMap()
        check(responses.size == size) {
            "Duplicate key/value key declared. Make sure all key/value keys are unique."
        }
        return responses
    }
}
