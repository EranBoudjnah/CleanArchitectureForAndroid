package com.mitteloupe.whoami.history.ui.mapper

import android.content.res.Resources
import com.mitteloupe.whoami.history.presentation.model.SavedIpAddressRecordPresentationModel
import com.mitteloupe.whoami.history.ui.R
import com.mitteloupe.whoami.history.ui.model.HistoryRecordUiModel

class HistoryRecordToUiMapper(private val resources: Resources) {
    fun toUi(savedRecord: SavedIpAddressRecordPresentationModel, highlightedIpAddress: String?) =
        HistoryRecordUiModel(
            ipAddress = savedRecord.ipAddress,
            location = when {
                savedRecord.city.isNotNullOrBlank() && savedRecord.postCode.isNotNullOrBlank() -> {
                    resources.getString(
                        R.string.history_record_location_full_format,
                        savedRecord.city,
                        savedRecord.postCode
                    )
                }

                savedRecord.city.isNotNullOrBlank() -> {
                    resources.getString(
                        R.string.history_record_location_city_format,
                        savedRecord.city
                    )
                }

                savedRecord.postCode.isNotNullOrBlank() -> {
                    resources.getString(
                        R.string.history_record_location_postcode_format,
                        savedRecord.postCode
                    )
                }

                else -> resources.getString(R.string.history_record_location_unknown)
            },
            savedAtTimestampMilliseconds = savedRecord.savedAtTimestampMilliseconds,
            isHighlighted = savedRecord.ipAddress == highlightedIpAddress
        )

    private fun String?.isNotNullOrBlank() = !isNullOrBlank()
}
