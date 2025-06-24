package com.dev.exploreminsk.domain.repository

import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.domain.model.Place

interface PlacesRepository {
    suspend fun getPlaces(): Result<List<Place>>
    suspend fun getPlacesByCategory(category: Category): Result<List<Place>>
    suspend fun getPlacesByNameAndCategory(query: String, categoryId: Long?): Result<List<Place>>
}