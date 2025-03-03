package com.mitteloupe.whoami.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    circleColor: Color = MaterialTheme.colorScheme.primary,
    size: Dp = 64.dp,
    thickness: Dp = 4.dp,
    animationDelayMillis: Int = 1000
) {
    var circleScale by remember {
        mutableFloatStateOf(0f)
    }

    val circleScaleAnimated = animateFloatAsState(
        targetValue = circleScale,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDelayMillis)
        ),
        label = "LoadingAnimation"
    )

    LaunchedEffect(Unit) {
        circleScale = 1f
    }

    Box(
        modifier = modifier
            .size(size = size)
            .scale(scale = circleScaleAnimated.value)
            .border(
                width = thickness,
                color = circleColor.copy(alpha = 1f - circleScaleAnimated.value),
                shape = CircleShape
            )
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    LoadingAnimation()
}
