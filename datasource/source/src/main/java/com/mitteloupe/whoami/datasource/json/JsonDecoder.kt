package com.mitteloupe.whoami.datasource.json

interface JsonDecoder<TYPE> {
    fun decode(json: String): TYPE
}
