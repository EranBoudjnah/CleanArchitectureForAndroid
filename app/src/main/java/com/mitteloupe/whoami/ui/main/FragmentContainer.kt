package com.mitteloupe.whoami.ui.main

import android.util.Log
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
import androidx.fragment.app.commitNow

@Composable
fun FragmentContainer(
    containerId: Int,
    fragmentManager: FragmentManager,
    commit: FragmentTransaction.(containerId: Int) -> Unit,
    onFragmentViewCreated: (containerId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var initialized by rememberSaveable {
        Log.d("FragmentContainer", "initialized initialized")
        mutableStateOf(false)
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            Log.d("FragmentContainer", "Factory initialized")
            FragmentContainerView(context)
                .apply { id = containerId }
        },
        update = { view ->
            Log.d("FragmentContainer", "Update called ($initialized)")
            if (initialized) {
                fragmentManager.onContainerAvailable(view)
                onFragmentViewCreated(view.id)
            } else {
                fragmentManager.commitNow { commit(view.id) }
                initialized = true
            }
        }
    )
}
