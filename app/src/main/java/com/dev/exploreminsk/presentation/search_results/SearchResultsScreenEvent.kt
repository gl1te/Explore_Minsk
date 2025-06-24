package com.dev.exploreminsk.presentation.search_results

import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.presentation.explore.ExploreScreenEvent

sealed class SearchResultsScreenEvent {
    data class OnSearchQueryChange(val query: String): SearchResultsScreenEvent()
    data class OnCategorySelected(val category: Category): SearchResultsScreenEvent()
}