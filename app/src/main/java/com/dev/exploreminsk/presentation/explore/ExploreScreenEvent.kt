package com.dev.exploreminsk.presentation.explore

import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.domain.model.Place

sealed class ExploreScreenEvent {
    data class OnPlaceDoubleClick(val place: Place): ExploreScreenEvent()
    data class OnCategorySelected(val category: Category): ExploreScreenEvent()
}