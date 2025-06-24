package com.dev.exploreminsk.presentation.locations

import com.dev.exploreminsk.domain.model.Place

data class LocationsScreenState(
    val isLoading: Boolean = false,
    val places: List<Place> = emptyList(),
    val selectedCategory: String = "",
    val errorMessage: String? = null
)
