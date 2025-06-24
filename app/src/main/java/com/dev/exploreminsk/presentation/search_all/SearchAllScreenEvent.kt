package com.dev.exploreminsk.presentation.search_all

import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.presentation.explore.ExploreScreenEvent

sealed class SearchAllScreenEvent {
    data class OnSearchQueryChange(val query: String): SearchAllScreenEvent()
}