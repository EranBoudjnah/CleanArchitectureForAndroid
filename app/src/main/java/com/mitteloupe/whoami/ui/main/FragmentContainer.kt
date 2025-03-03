package com.mitteloupe.whoami.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit

@Composable
fun FragmentContainer(
    containerId: Int,
    fragmentManager: FragmentManager,
    commit: FragmentTransaction.(containerId: Int) -> Unit,
    onFragmentViewCreated: (containerId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var initialized by rememberSaveable { mutableStateOf(false) }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            FragmentContainerView(context)
                .apply { id = containerId }
        },
        update = { view ->
            if (initialized) {
                fragmentManager.onContainerAvailable(view)
                onFragmentViewCreated(view.id)
            } else {
                fragmentManager.commit { commit(view.id) }
                initialized = true
            }
        }
    )
}
