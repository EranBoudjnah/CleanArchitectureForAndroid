package com.mitteloupe.whoami.history.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavHostController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.mitteloupe.whoami.architecture.ui.BaseFragment
import com.mitteloupe.whoami.architecture.ui.binder.ViewStateBinder
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.DestinationToUiMapper
import com.mitteloupe.whoami.architecture.ui.view.ViewsProvider
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState
import com.mitteloupe.whoami.history.presentation.viewmodel.HistoryViewModel
import com.mitteloupe.whoami.history.ui.R
import com.mitteloupe.whoami.history.ui.binder.HistoryViewStateBinder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class HistoryFragment :
    BaseFragment<HistoryViewState, Any>(R.layout.fragment_history), HistoryViewsProvider {
    lateinit var navHostController: NavHostController

    @Inject
    override lateinit var viewModel: HistoryViewModel

    @Inject
    @Named(NAVIGATION_MAPPER_NAME)
    override lateinit var destinationToUiMapper: DestinationToUiMapper

    override lateinit var noRecordsView: View

    override lateinit var recordsListView: RecyclerView

    @Inject
    @JvmSuppressWildcards
    override lateinit var viewStateBinder: ViewStateBinder<HistoryViewState, ViewsProvider>

    private val viewStateStore: HistoryViewStateBinder
        get() = viewStateBinder as HistoryViewStateBinder

    override fun View.bindViews() {
        noRecordsView = findViewById(R.id.history_no_records_view)
        recordsListView = findViewById(R.id.history_records_list)
        val toolbar = findViewById<MaterialToolbar>(R.id.history_toolbar)
        toolbar.setNavigationIcon(R.drawable.icon_back)
        toolbar.title = "Hello"
        toolbar.setNavigationOnClickListener {
            viewModel.onBackAction()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.onEnter()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        with(viewStateStore) {
            savedInstanceState?.restoreState(this@HistoryFragment)
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(viewStateStore) {
            outState.saveState()
        }
    }

    fun setNavController(navController: NavHostController) {
        navHostController = navController
    }

    companion object {
        const val NAVIGATION_MAPPER_NAME = "History"

        fun newInstance() = HistoryFragment()
    }
}
