package com.dev.exploreminsk.presentation.favorite.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun FavoriteSwappableItemWithActions(
    actions: @Composable RowScope.(onDelete: () -> Unit) -> Unit,
    content: @Composable () -> Unit,
    onRemove: () -> Unit
) {
    var isRemoving by remember { mutableStateOf(false) }
    val offset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    var contextMenuWidth by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(isRemoving) {
        if (isRemoving) {
            delay(300)
            onRemove()
        }
    }

    AnimatedVisibility(
        visible = !isRemoving,
        exit = fadeOut(tween(300)) + shrinkVertically(tween(300))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .onSizeChanged {
                        contextMenuWidth = it.width.toFloat()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                actions {
                    isRemoving = true
                }
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        IntOffset(-offset.value.roundToInt(), 0)
                    }
                    .pointerInput(contextMenuWidth) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, dragAmount ->
                                scope.launch {
                                    val newOffset = (offset.value - dragAmount).coerceIn(0f, contextMenuWidth)
                                    offset.snapTo(newOffset)
                                }
                            },
                            onDragEnd = {
                                scope.launch {
                                    if (offset.value > contextMenuWidth / 2) {
                                        offset.animateTo(contextMenuWidth)
                                    } else {
                                        offset.animateTo(0f)
                                    }
                                }
                            }
                        )
                    }
            ) {
                content()
            }
        }
    }
}