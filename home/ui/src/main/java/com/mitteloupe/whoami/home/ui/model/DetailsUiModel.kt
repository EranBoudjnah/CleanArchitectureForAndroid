package com.mitteloupe.whoami.home.ui.model

import androidx.compose.runtime.Immutable

@Immutable
data class DetailsUiModel(val details: List<Pair<String, IconLabelUiModel?>>)
