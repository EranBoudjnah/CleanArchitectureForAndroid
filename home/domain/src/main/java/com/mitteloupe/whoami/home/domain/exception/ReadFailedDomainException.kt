package com.mitteloupe.whoami.home.domain.exception

import com.mitteloupe.whoami.architecture.domain.exception.DomainException

class ReadFailedDomainException(throwable: Throwable) : DomainException(throwable)
