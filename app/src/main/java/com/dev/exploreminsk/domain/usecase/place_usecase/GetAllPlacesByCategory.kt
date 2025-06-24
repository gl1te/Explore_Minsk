package com.dev.exploreminsk.domain.usecase.place_usecase

import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.domain.repository.PlacesRepository
import javax.inject.Inject

class GetAllPlacesByCategory @Inject constructor(
    private val placesRepository: PlacesRepository
) {

    suspend operator fun invoke(category: Category): Result<List<Place>> {
        return placesRepository.getPlacesByCategory(category)
    }

}