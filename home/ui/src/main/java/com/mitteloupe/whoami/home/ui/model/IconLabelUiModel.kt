package com.mitteloupe.whoami.home.ui.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class IconLabelUiModel(@DrawableRes val iconResourceId: Int, val label: String) : Parcelable
