package com.mitteloupe.whoami.datasource.remote.exception

import com.mitteloupe.whoami.datasource.architecture.exception.DataException

data class RequestTimeoutDataException(override val cause: Throwable) : DataException(cause)
