package com.mitteloupe.whoami.ui.main.route

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
object Home : Parcelable

@Parcelize
data class History(val highlightedIpAddress: String?) : Parcelable

@Parcelize
object OpenSourceNotices : Parcelable
