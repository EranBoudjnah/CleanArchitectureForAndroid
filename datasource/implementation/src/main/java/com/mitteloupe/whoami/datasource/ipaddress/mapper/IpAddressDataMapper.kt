package com.mitteloupe.whoami.datasource.ipaddress.mapper

import com.mitteloupe.whoami.datasource.ipaddress.model.IpAddressApiModel

class IpAddressDataMapper {
    fun toData(ipAddress: IpAddressApiModel) = ipAddress.ipAddress
}
