package com.mitteloupe.whoami.architecture.ui.binder

import com.mitteloupe.whoami.architecture.ui.view.ViewsProvider

interface ViewStateBinder<in VIEW_STATE : Any, in VIEWS_PROVIDER : ViewsProvider> {
    fun VIEWS_PROVIDER.bindState(viewState: VIEW_STATE)
}
