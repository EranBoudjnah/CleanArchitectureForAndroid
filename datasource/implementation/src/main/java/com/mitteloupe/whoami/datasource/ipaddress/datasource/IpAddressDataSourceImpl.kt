package com.mitteloupe.whoami.datasource.ipaddress.datasource

import com.mitteloupe.whoami.datasource.ipaddress.exception.NoIpAddressDataException
import com.mitteloupe.whoami.datasource.ipaddress.mapper.IpAddressToDataMapper
import com.mitteloupe.whoami.datasource.ipaddress.service.IpAddressService
import com.mitteloupe.whoami.datasource.remote.provider.retrofit.fetchBodyOrThrow

class IpAddressDataSourceImpl(
    private val lazyIpAddressService: Lazy<IpAddressService>,
    private val ipAddressToDataMapper: IpAddressToDataMapper
) : IpAddressDataSource {
    private val ipAddressService by lazy {
        lazyIpAddressService.value
    }

    override fun ipAddress(): String = ipAddressToDataMapper.toData(
        ipAddressService.ipAddress().fetchBodyOrThrow {
            NoIpAddressDataException()
        }
    )
}
