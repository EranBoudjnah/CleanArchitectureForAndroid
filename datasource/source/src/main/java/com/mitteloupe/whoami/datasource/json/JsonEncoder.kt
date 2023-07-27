package com.mitteloupe.whoami.datasource.json

interface JsonEncoder<TYPE> {
    fun encode(value: TYPE): String
}
