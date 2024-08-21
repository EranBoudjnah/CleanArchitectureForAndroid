package com.mitteloupe.whoami.datasource.json

import com.squareup.moshi.JsonAdapter

class JsonProcessor<T>(private val jsonAdapter: JsonAdapter<T>) :
    JsonEncoder<T>,
    JsonDecoder<T> {
    override fun decode(json: String): T? = jsonAdapter.fromJson(json)

    override fun encode(value: T): String = jsonAdapter.toJson(value)
}
