package com.mitteloupe.whoami.home.ui.view.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.expandVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.mitteloupe.whoami.home.ui.content.ErrorContent
import com.mitteloupe.whoami.home.ui.model.ErrorUiModel
import com.mitteloupe.whoami.home.ui.view.spring.enterSpring
import com.mitteloupe.whoami.home.ui.view.widget.preview.ErrorPreviewParameterProvider

@Composable
fun ErrorContentContainer(visible: Boolean, errorText: String) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(
            animationSpec = enterSpring(IntSize.VisibilityThreshold)
        ) + slideInVertically(
            animationSpec = enterSpring(IntOffset.VisibilityThreshold)
        )
    ) {
        ErrorContent(
            errorText = errorText,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview(@PreviewParameter(ErrorPreviewParameterProvider::class) error: ErrorUiModel) {
    ErrorContentContainer(visible = true, errorText = error.message)
}
