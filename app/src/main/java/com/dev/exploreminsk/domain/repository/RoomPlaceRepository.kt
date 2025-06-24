package com.dev.exploreminsk.domain.repository

import com.dev.exploreminsk.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface RoomPlaceRepository {
    fun getPlaces(): Flow<List<Place>>
    suspend fun upsertPlace(place: Place)
    suspend fun deleteArticle(id: Long)
}
