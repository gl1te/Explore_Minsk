package com.dev.exploreminsk.presentation.favorite.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dev.exploreminsk.domain.model.Place

@Composable
fun FavoritesList(
    places: List<Place>,
    onPlaceClick: (Place) -> Unit,
    onDeleteClick: (Place) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        itemsIndexed(items = places, key = { _, place -> place.id }) { _, place ->
            FavoriteSwappableItemWithActions(
                actions = { onDelete ->
                    DeleteButton {
                        onDelete()
                    }
                },
                content = {
                    FavoritesItem(
                        place = place,
                        onPlaceClick = {
                            onPlaceClick(place)
                        }
                    )
                },
                onRemove = {
                    onDeleteClick(place)
                }
            )
        }
    }
}