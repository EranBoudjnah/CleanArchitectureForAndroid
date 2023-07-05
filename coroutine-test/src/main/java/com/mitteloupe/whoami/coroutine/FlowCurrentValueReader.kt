package com.mitteloupe.whoami.coroutine

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

suspend fun <VALUE_TYPE> Flow<VALUE_TYPE>.currentValue() =
    take(1).toList().first()
