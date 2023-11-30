package com.mitteloupe.whoami.home.data.mapper

import com.mitteloupe.whoami.architecture.domain.exception.DomainException
import com.mitteloupe.whoami.architecture.domain.exception.UnknownDomainException
import com.mitteloupe.whoami.datasource.ipaddress.exception.NoIpAddressDataException
import com.mitteloupe.whoami.datasource.remote.exception.RequestTimeoutDataException
import com.mitteloupe.whoami.home.domain.exception.ReadFailedDomainException

class ThrowableToDomainMapper {
    fun toDomain(exception: Throwable): DomainException = when (exception) {
        is NoIpAddressDataException -> ReadFailedDomainException(exception)
        is RequestTimeoutDataException -> ReadFailedDomainException(exception)
        else -> UnknownDomainException(exception)
    }
}
