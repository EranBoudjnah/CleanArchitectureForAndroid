package com.mitteloupe.whoami.datasource.ipaddressinformation.datasource

import com.mitteloupe.whoami.datasource.ipaddressinformation.exception.NoIpAddressInformationDataException
import com.mitteloupe.whoami.datasource.ipaddressinformation.model.IpAddressInformationDataModel

interface IpAddressInformationDataSource {
    @Throws(NoIpAddressInformationDataException::class)
    fun ipAddressInformation(ipAddress: String): IpAddressInformationDataModel
}
