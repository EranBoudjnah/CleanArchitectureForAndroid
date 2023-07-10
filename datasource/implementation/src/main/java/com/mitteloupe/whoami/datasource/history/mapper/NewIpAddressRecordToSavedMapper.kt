package com.mitteloupe.whoami.datasource.history.mapper

import com.mitteloupe.whoami.datasource.history.model.NewIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.time.NowProvider

class NewIpAddressRecordToSavedMapper(
    private val nowProvider: NowProvider
) {
    fun toSaved(historyRecord: NewIpAddressHistoryRecordDataModel) =
        SavedIpAddressHistoryRecordDataModel(
            ipAddress = historyRecord.ipAddress,
            city = historyRecord.city,
            region = historyRecord.region,
            countryCode = historyRecord.countryCode,
            geolocation = historyRecord.geolocation,
            internetServiceProviderName = historyRecord.internetServiceProviderName,
            postCode = historyRecord.postCode,
            timeZone = historyRecord.timeZone,
            savedAtTimestampMilliseconds = nowProvider.nowMilliseconds()
        )
}
