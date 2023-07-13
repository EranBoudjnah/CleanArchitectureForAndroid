package com.mitteloupe.whoami.history.ui.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mitteloupe.whoami.architecture.ui.BaseFragment
import com.mitteloupe.whoami.architecture.ui.binder.ViewStateBinder
import com.mitteloupe.whoami.architecture.ui.view.ViewsProvider
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState
import com.mitteloupe.whoami.history.presentation.viewmodel.HistoryViewModel
import com.mitteloupe.whoami.home.ui.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : BaseFragment<HistoryViewState, Any>(
    R.layout.fragment_history
), HistoryViewsProvider {
    @Inject
    override lateinit var viewModel: HistoryViewModel

    override lateinit var recordsListView: RecyclerView

    @Inject
    @JvmSuppressWildcards
    override lateinit var viewStateBinder: ViewStateBinder<HistoryViewState, ViewsProvider>

    override fun View.bindViews() {
        recordsListView = findViewById(R.id.history_records_list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.onEnter()
        }
    }
}
