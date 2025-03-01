package com.mitteloupe.whoami.history.presentation.mapper

import com.mitteloupe.whoami.history.domain.model.SavedIpAddressRecordDomainModel
import com.mitteloupe.whoami.history.presentation.model.SavedIpAddressRecordPresentationModel

class SavedIpAddressRecordPresentationMapper {
    fun toPresentation(savedRecord: SavedIpAddressRecordDomainModel) =
        SavedIpAddressRecordPresentationModel(
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
