package com.mitteloupe.whoami.history.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryRecordUiModel(
    val ipAddress: String
) : Parcelable
