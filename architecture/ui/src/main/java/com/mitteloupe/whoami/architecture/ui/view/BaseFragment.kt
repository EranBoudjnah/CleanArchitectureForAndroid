package com.mitteloupe.whoami.architecture.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationDestination
import com.mitteloupe.whoami.architecture.presentation.notification.PresentationNotification
import com.mitteloupe.whoami.architecture.presentation.viewmodel.BaseViewModel
import com.mitteloupe.whoami.architecture.ui.binder.ViewStateBinder
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.DestinationToUiMapper
import kotlinx.coroutines.launch

abstract class BaseFragment<VIEW_STATE : Any, NOTIFICATION : PresentationNotification> :
    Fragment, ViewsProvider {
    constructor() : super()
    constructor(@LayoutRes layoutResourceId: Int) : super(layoutResourceId)

    abstract val viewModel: BaseViewModel<VIEW_STATE, NOTIFICATION>

    abstract val viewStateBinder: ViewStateBinder<VIEW_STATE, ViewsProvider>

    abstract val destinationToUiMapper: DestinationToUiMapper

    open val navController: NavController
        get() = findNavController()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        view?.bindViews()
        observeViewModel()
        return view
    }

    abstract fun View.bindViews()

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect(::applyViewState)
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.destination.collect(::navigate)
            }
        }
    }

    private fun applyViewState(viewState: VIEW_STATE) {
        with(viewStateBinder) {
            bindState(viewState)
        }
    }

    private fun navigate(destination: PresentationDestination) {
        val uiDestination = destinationToUiMapper.toUi(destination)
        uiDestination.navigate(navController)
    }
}
