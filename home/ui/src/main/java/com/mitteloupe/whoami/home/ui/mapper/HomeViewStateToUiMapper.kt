package com.mitteloupe.whoami.home.ui.mapper

import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.ui.model.HomeViewStateUiModel

class HomeViewStateToUiMapper {
    fun toUi(viewState: HomeViewState) = when (viewState) {
        is HomeViewState.Connected -> HomeViewStateUiModel.Connected
        is HomeViewState.Disconnected -> HomeViewStateUiModel.Disconnected
        is HomeViewState.Error -> HomeViewStateUiModel.Error
        is HomeViewState.Loading -> HomeViewStateUiModel.Loading
    }
}
