package com.dev.exploreminsk.domain.usecase.room_usecase

import com.dev.exploreminsk.domain.repository.RoomPlaceRepository
import javax.inject.Inject

class DeletePlaceUseCase @Inject constructor(
    private val roomPlaceRepository: RoomPlaceRepository
) {

    suspend operator fun invoke(id: Long) {
        roomPlaceRepository.deleteArticle(id)
    }

}