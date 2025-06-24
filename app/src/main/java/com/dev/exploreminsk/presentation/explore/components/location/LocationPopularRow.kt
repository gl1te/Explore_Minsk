package com.dev.exploreminsk.presentation.explore.components.location

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.dev.exploreminsk.R
import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.presentation.common.LocationItem

@Composable
fun LocationPopularRow(
    items: List<Place>,
    onPlaceClick: (Place) -> Unit,
    onPlaceDoubleClick: (Place) -> Unit,
) {
    val listState = rememberLazyListState()
    val firstVisibleIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(30.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        state = listState
    ) {
        itemsIndexed(items) { index, item ->

            val isNextItem = index == firstVisibleIndex.value + 1

            val scale by animateFloatAsState(
                targetValue = if (isNextItem) 1.08f else 1f,
                label = "scale"
            )

            LocationItem(
                place = item,
                modifier = Modifier
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                    )
                    .then(
                        if (isNextItem) Modifier.shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(24.dp),
                            ambientColor = colorResource(R.color.custom_shadow_card),
                            spotColor = colorResource(R.color.custom_shadow_card)
                        ) else Modifier
                    ),
                onPlaceClick = {
                    onPlaceClick(item)
                },
                onPlaceDoubleClick = {
                    onPlaceDoubleClick(item)
                }
            )
        }
    }
}