package com.mitteloupe.whoami.datasource.ipaddressinformation.mapper

import com.mitteloupe.whoami.datasource.ipaddressinformation.model.IpAddressInformationApiModel
import com.mitteloupe.whoami.datasource.ipaddressinformation.model.IpAddressInformationDataModel

class IpAddressInformationDataMapper {
    fun toData(ipAddress: IpAddressInformationApiModel) = IpAddressInformationDataModel(
        city = ipAddress.city,
        region = ipAddress.region,
        country = ipAddress.country,
        geolocation = ipAddress.geolocation,
        internetServiceProviderName = ipAddress.internetServiceProviderName,
        postCode = ipAddress.postCode,
        timeZone = ipAddress.timeZone
    )
}
