package com.mitteloupe.whoami.datasource.ipaddress.mapper

import com.mitteloupe.whoami.datasource.ipaddress.model.IpAddressApiModel

class IpAddressToDataMapper {
    fun toData(ipAddress: IpAddressApiModel) = ipAddress.ipAddress
}
