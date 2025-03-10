package com.mitteloupe.whoami.datasource.ipaddress.datasource

import com.mitteloupe.whoami.datasource.ipaddress.exception.NoIpAddressDataException

interface IpAddressDataSource {
    @Throws(NoIpAddressDataException::class)
    fun ipAddress(): String
}
