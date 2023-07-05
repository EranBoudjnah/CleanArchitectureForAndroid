package com.mitteloupe.whoami.datasource.ipaddressinformation.datasource

import com.mitteloupe.whoami.datasource.ipaddressinformation.model.IpAddressInformationDataModel

interface IpAddressInformationDataSource {
    fun ipAddressInformation(ipAddress: String): IpAddressInformationDataModel
}
