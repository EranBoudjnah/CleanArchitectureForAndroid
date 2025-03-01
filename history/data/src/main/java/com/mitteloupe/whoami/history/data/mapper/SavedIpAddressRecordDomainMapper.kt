package com.mitteloupe.whoami.history.data.mapper

import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.history.domain.model.SavedIpAddressRecordDomainModel

class SavedIpAddressRecordDomainMapper {
    fun toDomain(savedRecord: SavedIpAddressHistoryRecordDataModel) =
        SavedIpAddressRecordDomainModel(
            savedRecord.ipAddress,
            savedRecord.city,
            savedRecord.region,
            savedRecord.countryCode,
            savedRecord.geolocation,
            savedRecord.internetServiceProviderName,
            savedRecord.postCode,
            savedRecord.timeZone,
            savedRecord.savedAtTimestampMilliseconds
        )
}
