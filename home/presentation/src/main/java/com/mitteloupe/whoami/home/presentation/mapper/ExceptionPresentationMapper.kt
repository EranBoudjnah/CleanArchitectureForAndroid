package com.mitteloupe.whoami.home.presentation.mapper

import com.mitteloupe.whoami.architecture.domain.exception.DomainException
import com.mitteloupe.whoami.home.domain.exception.NoIpAddressDomainException
import com.mitteloupe.whoami.home.domain.exception.NoIpAddressInformationDomainException
import com.mitteloupe.whoami.home.domain.exception.ReadFailedDomainException
import com.mitteloupe.whoami.home.presentation.model.ErrorPresentationModel.NoIpAddress
import com.mitteloupe.whoami.home.presentation.model.ErrorPresentationModel.NoIpAddressInformation
import com.mitteloupe.whoami.home.presentation.model.ErrorPresentationModel.RequestTimeout
import com.mitteloupe.whoami.home.presentation.model.ErrorPresentationModel.Unknown

class ExceptionPresentationMapper {
    fun toPresentation(exception: DomainException) = when (exception) {
        is ReadFailedDomainException -> RequestTimeout
        is NoIpAddressDomainException -> NoIpAddress
        is NoIpAddressInformationDomainException -> NoIpAddressInformation(exception.ipAddress)
        else -> Unknown
    }
}
