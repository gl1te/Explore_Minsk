package com.dev.exploreminsk.domain.usecase.room_usecase

import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.domain.repository.RoomPlaceRepository
import javax.inject.Inject

class UpsertPlaceUseCase @Inject  constructor(
    private val roomPlaceRepository: RoomPlaceRepository
) {

    suspend operator fun invoke(place: Place){
        roomPlaceRepository.upsertPlace(place)
    }

}