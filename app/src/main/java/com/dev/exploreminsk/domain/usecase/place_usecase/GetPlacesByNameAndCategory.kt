package com.dev.exploreminsk.domain.usecase.place_usecase

import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.domain.repository.PlacesRepository
import javax.inject.Inject

class GetPlacesByNameAndCategory @Inject constructor(
    private val placesRepository: PlacesRepository
) {

    suspend operator fun invoke(query: String, categoryId: Long?): Result<List<Place>>{
        return placesRepository.getPlacesByNameAndCategory(
            query,
            categoryId = categoryId
        )
    }

}