package com.dev.exploreminsk.presentation.favorite

import com.dev.exploreminsk.domain.model.Place
import kotlinx.coroutines.flow.Flow

data class FavoritesScreenState(
    val isLoading: Boolean = false,
    val places: List<Place> = emptyList(),
    val errorMessage: String? = null
)
