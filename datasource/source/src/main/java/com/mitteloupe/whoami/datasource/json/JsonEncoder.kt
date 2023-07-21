package com.mitteloupe.whoami.datasource.json

interface JsonEncoder<TYPE> {
    fun encode(json: TYPE): String
}
