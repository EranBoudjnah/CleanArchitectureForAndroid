package com.mitteloupe.whoami.ui.theme

import android.content.Context
import android.util.TypedValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource

@Composable
fun getColor(color: Int): Color =
    colorResource(LocalContext.current.getColorFromAttrs(color).resourceId)

private fun Context.getColorFromAttrs(attr: Int) =
    TypedValue().apply {
        theme.resolveAttribute(attr, this, true)
    }
