package com.mitteloupe.whoami.history.ui.view

import androidx.recyclerview.widget.RecyclerView
import com.mitteloupe.whoami.architecture.ui.view.ViewsProvider

interface HistoryViewsProvider : ViewsProvider {
    var recordsListView: RecyclerView
}
