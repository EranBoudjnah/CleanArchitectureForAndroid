package com.mitteloupe.whoami.datasource.ipaddress.datasource

import com.mitteloupe.whoami.datasource.ipaddress.exception.NoIpAddressDataException
import com.mitteloupe.whoami.datasource.ipaddress.mapper.IpAddressDataMapper
import com.mitteloupe.whoami.datasource.ipaddress.service.IpAddressService
import com.mitteloupe.whoami.datasource.remote.provider.retrofit.fetchBodyOrThrow

class IpAddressDataSourceImpl(
    private val lazyIpAddressService: Lazy<IpAddressService>,
    private val ipAddressDataMapper: IpAddressDataMapper
) : IpAddressDataSource {
    private val ipAddressService by lazy {
        lazyIpAddressService.value
    }

    override fun ipAddress(): String = ipAddressDataMapper.toData(
        ipAddressService.ipAddress().fetchBodyOrThrow {
            NoIpAddressDataException()
        }
    )
}
