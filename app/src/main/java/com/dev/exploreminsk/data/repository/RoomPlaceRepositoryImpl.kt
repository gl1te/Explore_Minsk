package com.dev.exploreminsk.data.repository

import com.dev.exploreminsk.data.database.ExploreDao
import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.domain.repository.RoomPlaceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomPlaceRepositoryImpl @Inject constructor(
    private val exploreDao: ExploreDao
): RoomPlaceRepository {

    override fun getPlaces(): Flow<List<Place>> {
        return exploreDao.getPlaces()
    }

    override suspend fun upsertPlace(place: Place) {
        exploreDao.upsert(place)
    }

    override suspend fun deleteArticle(id: Long) {
        exploreDao.delete(id)
    }

}