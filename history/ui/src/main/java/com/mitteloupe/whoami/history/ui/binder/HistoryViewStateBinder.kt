package com.mitteloupe.whoami.history.ui.binder

import com.mitteloupe.whoami.architecture.ui.binder.ViewStateBinder
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState
import com.mitteloupe.whoami.history.ui.view.HistoryViewsProvider

class HistoryViewStateBinder : ViewStateBinder<HistoryViewState, HistoryViewsProvider> {
    override fun HistoryViewsProvider.bindState(viewState: HistoryViewState) = Unit
}
