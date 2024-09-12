package com.mitteloupe.whoami.history.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.analytics.event.Click
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent
import com.mitteloupe.whoami.architecture.presentation.notification.PresentationNotification
import com.mitteloupe.whoami.architecture.ui.binder.ViewStateBinder
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.NavigationEventToDestinationMapper
import com.mitteloupe.whoami.architecture.ui.view.BaseFragment
import com.mitteloupe.whoami.architecture.ui.view.ViewsProvider
import com.mitteloupe.whoami.history.presentation.model.HistoryViewState
import com.mitteloupe.whoami.history.presentation.viewmodel.HistoryViewModel
import com.mitteloupe.whoami.history.ui.R
import com.mitteloupe.whoami.history.ui.adapter.HistoryAdapter
import com.mitteloupe.whoami.history.ui.binder.HistoryViewStateBinder
import com.mitteloupe.whoami.history.ui.mapper.HistoryRecordDeletionToPresentationMapper
import com.mitteloupe.whoami.history.ui.model.HistoryRecordUiModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

private typealias NavigationMapper = NavigationEventToDestinationMapper<PresentationNavigationEvent>

@AndroidEntryPoint
class HistoryFragment :
    BaseFragment<HistoryViewState, PresentationNotification>(R.layout.fragment_history),
    HistoryViewsProvider,
    HistoryAdapter.UserEventListener {
    override lateinit var navController: NavController

    @Inject
    override lateinit var viewModel: HistoryViewModel

    @Inject
    @JvmSuppressWildcards
    @Named(NAVIGATION_MAPPER_NAME)
    override lateinit var navigationEventToDestinationMapper: NavigationMapper

    @Inject
    lateinit var recordDeletionToPresentationMapper: HistoryRecordDeletionToPresentationMapper

    @Inject
    lateinit var analytics: Analytics

    override lateinit var noRecordsView: View

    override lateinit var recordsListView: RecyclerView

    @Inject
    @JvmSuppressWildcards
    override lateinit var viewStateBinder: ViewStateBinder<HistoryViewState, ViewsProvider>

    private val viewStateStore: HistoryViewStateBinder
        get() = viewStateBinder as HistoryViewStateBinder

    private val highlightedIpAddress: String?
        get() = arguments?.getString(ARGUMENT_HIGHLIGHTED_IP)

    override fun View.bindViews() {
        noRecordsView = findViewById(R.id.history_no_records_view)
        recordsListView = findViewById(R.id.history_records_list)
        val toolbar = findViewById<MaterialToolbar>(R.id.history_toolbar)
        toolbar.setNavigationIcon(R.drawable.icon_back)
        toolbar.title = "Hello"
        toolbar.setNavigationOnClickListener {
            analytics.logEvent(Click("Back"))
            viewModel.onBackAction()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.onEnter(highlightedIpAddress)
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

    override fun onResume() {
        super.onResume()
        analytics.logScreen("History")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(viewStateStore) {
            outState.saveState()
        }
    }

    override fun onDeleteClick(record: HistoryRecordUiModel) {
        val presentationRequest = recordDeletionToPresentationMapper.toDeletionPresentation(record)
        viewModel.onDeleteAction(presentationRequest)
    }

    companion object {
        const val NAVIGATION_MAPPER_NAME = "History"
        const val ARGUMENT_HIGHLIGHTED_IP = "Highlighted IP"

        fun newInstance(highlightedIpAddress: String?) = HistoryFragment().apply {
            arguments = bundleOf(ARGUMENT_HIGHLIGHTED_IP to highlightedIpAddress)
        }
    }
}
