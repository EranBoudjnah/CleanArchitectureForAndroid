package com.mitteloupe.whoami.home.ui.model

import androidx.annotation.DrawableRes

data class IconLabelUiModel(
    @DrawableRes val iconResourceId: Int,
    val label: String
)
