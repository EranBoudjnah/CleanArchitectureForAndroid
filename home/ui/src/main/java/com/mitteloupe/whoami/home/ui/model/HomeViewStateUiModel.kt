package com.mitteloupe.whoami.home.ui.model

sealed interface HomeViewStateUiModel {
    data object Loading : HomeViewStateUiModel

    data object Connected : HomeViewStateUiModel

    data object Disconnected : HomeViewStateUiModel

    data object Error : HomeViewStateUiModel
}
