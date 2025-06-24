package com.dev.exploreminsk.presentation.favorite

sealed class FavoritesOnEvent {
    data class OnDeletePlace(val id: Long): FavoritesOnEvent()
}