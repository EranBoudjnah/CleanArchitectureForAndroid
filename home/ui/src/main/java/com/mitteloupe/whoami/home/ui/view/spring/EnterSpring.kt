package com.mitteloupe.whoami.home.ui.view.spring

import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Stable

@Stable
fun <T> enterSpring(visibilityThreshold: T) = spring(
    dampingRatio = DampingRatioLowBouncy,
    stiffness = StiffnessMediumLow,
    visibilityThreshold = visibilityThreshold
)
