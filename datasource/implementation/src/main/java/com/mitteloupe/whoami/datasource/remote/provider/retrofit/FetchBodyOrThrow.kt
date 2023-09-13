package com.mitteloupe.whoami.datasource.remote.provider.retrofit

import com.mitteloupe.whoami.datasource.architecture.exception.DataException
import com.mitteloupe.whoami.datasource.remote.exception.RequestTimeoutDataException
import java.net.SocketTimeoutException
import retrofit2.Call

fun <T> Call<T>.fetchBodyOrThrow(exceptionProvider: () -> DataException) = try {
    execute().body() ?: throw exceptionProvider()
} catch (socketTimeoutException: SocketTimeoutException) {
    throw RequestTimeoutDataException(socketTimeoutException)
}
