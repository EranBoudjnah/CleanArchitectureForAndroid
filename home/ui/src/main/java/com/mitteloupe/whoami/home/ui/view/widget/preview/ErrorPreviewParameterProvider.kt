package com.mitteloupe.whoami.home.ui.view.widget.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ErrorPreviewParameterProvider : PreviewParameterProvider<ErrorPreviewModel> {
    override val values = sequenceOf(
        ErrorPreviewModel("Finding your IP address failed."),
        ErrorPreviewModel("Getting information about your IP address (192.168.0.0) failed."),
        ErrorPreviewModel("It seems the request to the server timed out."),
        ErrorPreviewModel("Oh no! Something went wrong.")
    )
}
