package com.dev.exploreminsk.presentation.locations.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.presentation.common.LocationItem

@Composable
fun LocationGrid(
    modifier: Modifier = Modifier,
    onPlaceClick: (Place) -> Unit,
    onPlaceDoubleClick: () -> Unit,
    places: List<Place>,
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        items(places) { place->
            LocationItem(
                modifier = Modifier
                    .height(220.dp)
                    .width(170.dp),
                onPlaceClick = {
                    onPlaceClick(place)
                },
                onPlaceDoubleClick = {
                    onPlaceDoubleClick()
                },
                place = place
            )
        }
    }
}