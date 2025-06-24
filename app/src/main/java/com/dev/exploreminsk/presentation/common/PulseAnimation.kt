package com.dev.exploreminsk.presentation.common

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.dev.exploreminsk.R

@Composable
fun PulseAnimation(
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition()
    val progress = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = progress.value
                scaleY = progress.value
                alpha = 1f - progress.value
            }
            .border(
                width = 5.dp,
                color = colorResource(R.color.custom_blue),
                shape = CircleShape
            )
    )
}