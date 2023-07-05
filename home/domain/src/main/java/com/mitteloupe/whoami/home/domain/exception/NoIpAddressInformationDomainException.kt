package com.mitteloupe.whoami.home.domain.exception

import com.mitteloupe.whoami.architecture.domain.exception.DomainException

data class NoIpAddressInformationDomainException(val ipAddress: String) : DomainException()
