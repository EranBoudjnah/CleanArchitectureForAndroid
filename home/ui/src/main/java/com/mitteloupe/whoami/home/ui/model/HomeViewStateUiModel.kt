package com.mitteloupe.whoami.home.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface HomeViewStateUiModel : Parcelable {
    @Parcelize
    data object Loading : HomeViewStateUiModel

    @Parcelize
    data object Connected : HomeViewStateUiModel

    @Parcelize
    data object Disconnected : HomeViewStateUiModel

    @Parcelize
    data object Error : HomeViewStateUiModel
}
