package com.mitteloupe.whoami.localstore

import com.mitteloupe.whoami.datasource.local.LocalStoreKey.KEY_HISTORY_RECORDS
import com.mitteloupe.whoami.test.asset.assetReader
import com.mitteloupe.whoami.test.localstore.KeyValueStore

const val KEY_VALUE_SAVED_HISTORY = "Saved history"
const val KEY_VALUE_NO_HISTORY = "No history"

class AppKeyValueStore : KeyValueStore() {
    override val internalKeyValues = listOf(
        KEY_VALUE_SAVED_HISTORY to
            (KEY_HISTORY_RECORDS to assetAsString("localstore/history_records.json")),
        KEY_VALUE_NO_HISTORY to
            (KEY_HISTORY_RECORDS to assetAsString("localstore/history_no_records.json"))
    )

    private fun assetAsString(assetName: String) = assetReader.getAssetAsString(assetName)
}
