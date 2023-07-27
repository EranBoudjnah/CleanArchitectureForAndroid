package com.mitteloupe.whoami.test.rule

import android.content.SharedPreferences
import androidx.core.content.edit
import com.mitteloupe.whoami.test.annotation.LocalStore
import com.mitteloupe.whoami.test.localstore.KeyValueStore
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class LocalStoreRule(
    private val lazySharedPreferences: Lazy<SharedPreferences>,
    private val lazyKeyValueStore: Lazy<KeyValueStore>
) : TestRule {
    override fun apply(
        base: Statement,
        description: Description
    ): Statement = LocalStoreInitializationStatement(
        lazySharedPreferences,
        lazyKeyValueStore,
        base,
        description
    )

    private class LocalStoreInitializationStatement(
        private val lazySharedPreferences: Lazy<SharedPreferences>,
        private val lazyKeyValueStore: Lazy<KeyValueStore>,
        private val base: Statement,
        private val description: Description
    ) : Statement() {
        private val sharedPreferences by lazy { lazySharedPreferences.value }
        private val keyValueStore by lazy { lazyKeyValueStore.value }

        override fun evaluate() {
            sharedPreferences.edit {
                clear()

                description.localStoreDataIds()
                    .map { localStoreDataId ->
                        requireNotNull(keyValueStore.keyValues[localStoreDataId]) {
                            "Request/Response ID $localStoreDataId not found."
                        }
                    }.forEach { keyValuePair ->
                        val (key, value) = keyValuePair
                        persistValue(key, value)
                    }
            }

            base.evaluate()

            sharedPreferences.edit {
                clear()
            }
        }

        private fun Description.localStoreDataIds() =
            annotations.filterIsInstance<LocalStore>()
                .flatMap { serverResponse -> serverResponse.localStoreDataIds.toList() }

        private fun SharedPreferences.Editor.persistValue(key: String, value: Any) = when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Boolean -> putBoolean(key, value)
            is Set<*> -> {
                @Suppress("UNCHECKED_CAST")
                putStringSet(key, value as Set<String>)
            }

            else -> throw IllegalArgumentException("$value is of an unsupported type.")
        }
    }
}
