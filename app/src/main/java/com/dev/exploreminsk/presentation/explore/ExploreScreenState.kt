package com.dev.exploreminsk.presentation.explore

import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.domain.model.Place

data class ExploreScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val categories: List<Category> = emptyList(),
    val popularPlaces: List<Place> = emptyList(),
    val recommendedPlaces: List<Place> = emptyList(),
    val selectedCategory: Category = Category(
        id = 0,
        name = "Locations"
    )
)
