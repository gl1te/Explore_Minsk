package com.dev.exploreminsk.domain.usecase.place_usecase

data class PlaceUseCases(
    val getAllPlacesUseCase: GetAllPlacesUseCase,
    val getAllPlacesByCategory: GetAllPlacesByCategory,
    val getPlacesByNameAndCategory: GetPlacesByNameAndCategory
)
