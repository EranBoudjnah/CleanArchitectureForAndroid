package com.mitteloupe.whoami.home.data.mapper

import com.mitteloupe.whoami.datasource.history.model.NewIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.home.domain.model.ConnectionDetailsDomainModel

class ConnectionDetailsDataMapper {
    fun toData(connectionDetails: ConnectionDetailsDomainModel.Connected) =
        NewIpAddressHistoryRecordDataModel(
            connectionDetails.ipAddress,
            connectionDetails.city,
            connectionDetails.region,
            connectionDetails.countryCode,
            connectionDetails.geolocation,
            connectionDetails.internetServiceProviderName,
            connectionDetails.postCode,
            connectionDetails.timeZone
        )
}
