package com.dev.exploreminsk.presentation.search_all

import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.domain.model.Place

data class SearchAllState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val places: List<Place> = emptyList(),
    val searchedPlaces: List<Place> = emptyList(),
    val selectedCategory: Category? = null
)
