package com.mitteloupe.whoami.datasource.history.mapper

import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordLocalModel

class SavedIpAddressRecordDataMapper {
    fun toData(historyRecord: SavedIpAddressHistoryRecordLocalModel) =
        SavedIpAddressHistoryRecordDataModel(
            ipAddress = historyRecord.ipAddress,
            city = historyRecord.city,
            region = historyRecord.region,
            countryCode = historyRecord.countryCode,
            geolocation = historyRecord.geolocation,
            internetServiceProviderName = historyRecord.internetServiceProviderName,
            postCode = historyRecord.postCode,
            timeZone = historyRecord.timeZone,
            savedAtTimestampMilliseconds = historyRecord.savedAtTimestampMilliseconds
        )
}
