package com.mitteloupe.whoami.history.ui.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mitteloupe.whoami.architecture.ui.view.ViewsProvider

interface HistoryViewsProvider : ViewsProvider {
    var noRecordsView: View
    val recordsListView: RecyclerView
}
