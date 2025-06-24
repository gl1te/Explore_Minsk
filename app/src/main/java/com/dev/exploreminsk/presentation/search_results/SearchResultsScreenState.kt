package com.dev.exploreminsk.presentation.search_results

import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.domain.model.Place

data class SearchResultsScreenState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val allPlaces: List<Place> = emptyList(),
    val searchedPlaces: List<Place> = emptyList(),
    val categories: List<Category> = emptyList(),
    val errorMessage: String? = null,
    val selectedCategory: Category? = null
)
