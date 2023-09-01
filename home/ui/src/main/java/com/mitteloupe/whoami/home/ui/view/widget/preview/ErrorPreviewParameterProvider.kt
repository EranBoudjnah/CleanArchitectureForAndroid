package com.mitteloupe.whoami.home.ui.view.widget.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.mitteloupe.whoami.home.ui.model.ErrorUiModel

class ErrorPreviewParameterProvider : PreviewParameterProvider<ErrorUiModel> {
    override val values = sequenceOf(
        ErrorUiModel("Finding your IP address failed."),
        ErrorUiModel("Getting information about your IP address (192.168.0.0) failed."),
        ErrorUiModel("It seems the request to the server timed out."),
        ErrorUiModel("Oh no! Something went wrong.")
    )
}
