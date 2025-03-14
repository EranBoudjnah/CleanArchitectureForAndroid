package com.mitteloupe.whoami.home.data.mapper

import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel.Connected
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel.Disconnected
import com.mitteloupe.whoami.datasource.connection.model.ConnectionStateDataModel.Unset
import com.mitteloupe.whoami.datasource.ipaddressinformation.model.IpAddressInformationDataModel
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel

class ConnectionDetailsDomainResolver {
    fun toDomain(
        connectionState: ConnectionStateDataModel,
        ipAddress: String?,
        ipAddressInformation: IpAddressInformationDataModel?
    ): ConnectionDetailsDomainModel = when (connectionState) {
        Connected -> {
            requireNotNull(ipAddress)
            ConnectionDetailsDomainModel.Connected(
                ipAddress = ipAddress,
                city = ipAddressInformation?.city,
                region = ipAddressInformation?.region,
                countryCode = ipAddressInformation?.country,
                geolocation = ipAddressInformation?.geolocation,
                internetServiceProviderName = ipAddressInformation?.internetServiceProviderName,
                postCode = ipAddressInformation?.postCode,
                timeZone = ipAddressInformation?.timeZone
            )
        }

        Disconnected -> ConnectionDetailsDomainModel.Disconnected
        Unset -> ConnectionDetailsDomainModel.Unset
    }
}
