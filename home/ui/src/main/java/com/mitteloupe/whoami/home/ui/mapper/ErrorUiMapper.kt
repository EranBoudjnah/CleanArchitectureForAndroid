package com.mitteloupe.whoami.home.ui.mapper

import android.content.res.Resources
import androidx.annotation.StringRes
import com.mitteloupe.whoami.home.presentation.model.ErrorPresentationModel
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.ui.R

class ErrorUiMapper(private val resources: Resources) {
    fun toUi(presentationError: HomeViewState.Error) = when (val error = presentationError.error) {
        ErrorPresentationModel.NoIpAddress -> string(R.string.home_error_no_ip_description)
        is ErrorPresentationModel.NoIpAddressInformation -> {
            resources.getString(
                R.string.home_error_no_information_description,
                error.ipAddress
            )
        }

        ErrorPresentationModel.RequestTimeout -> string(R.string.home_error_timeout_description)
        ErrorPresentationModel.Unknown -> string(R.string.home_error_unknown_description)
    }

    private fun string(@StringRes stringResourceId: Int) = resources.getString(stringResourceId)
}
