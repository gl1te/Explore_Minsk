package com.dev.exploreminsk.domain.usecase.room_usecase

data class RoomPlaceUseCases(
    val deletePlaceUseCase: DeletePlaceUseCase,
    val upsertPlaceUseCase: UpsertPlaceUseCase,
    val getPlacesUseCase: GetPlacesUseCase
)
