package com.mitteloupe.whoami.home.ui.view.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.expandVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.mitteloupe.whoami.home.ui.content.DisconnectedContent
import com.mitteloupe.whoami.home.ui.view.spring.enterSpring

@Composable
fun DisconnectedContentContainer(visible: Boolean) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(
            animationSpec = enterSpring(IntSize.VisibilityThreshold)
        ) + slideInVertically(
            animationSpec = enterSpring(IntOffset.VisibilityThreshold)
        )
    ) {
        DisconnectedContent(modifier = Modifier.fillMaxWidth())
    }
}

@Preview
@Composable
private fun Preview() {
    DisconnectedContentContainer(visible = true)
}
