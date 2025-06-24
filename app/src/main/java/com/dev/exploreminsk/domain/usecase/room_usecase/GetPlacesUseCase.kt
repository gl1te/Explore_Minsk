package com.dev.exploreminsk.domain.usecase.room_usecase

import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.domain.repository.RoomPlaceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlacesUseCase @Inject constructor(
    private val placesRepository: RoomPlaceRepository,
) {

    operator fun invoke(): Flow<List<Place>> {
        return placesRepository.getPlaces()
    }

}