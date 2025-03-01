package com.mitteloupe.whoami.datasource.ipaddressinformation.datasource

import com.mitteloupe.whoami.datasource.ipaddressinformation.exception.NoIpAddressInformationDataException
import com.mitteloupe.whoami.datasource.ipaddressinformation.mapper.IpAddressInformationDataMapper
import com.mitteloupe.whoami.datasource.ipaddressinformation.model.IpAddressInformationDataModel
import com.mitteloupe.whoami.datasource.ipaddressinformation.service.IpAddressInformationService
import com.mitteloupe.whoami.datasource.remote.provider.retrofit.fetchBodyOrThrow

class IpAddressInformationDataSourceImpl(
    private val lazyIpAddressInformationService: Lazy<IpAddressInformationService>,
    private val ipAddressInformationDataMapper: IpAddressInformationDataMapper
) : IpAddressInformationDataSource {
    private val ipAddressInformationService by lazy {
        lazyIpAddressInformationService.value
    }

    override fun ipAddressInformation(ipAddress: String): IpAddressInformationDataModel =
        ipAddressInformationDataMapper.toData(
            ipAddressInformationService.ipAddressInformation(ipAddress).fetchBodyOrThrow {
                NoIpAddressInformationDataException()
            }
        )
}
