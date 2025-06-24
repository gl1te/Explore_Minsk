package com.dev.exploreminsk.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.exploreminsk.domain.model.Place
import kotlinx.coroutines.flow.Flow

@Dao
interface ExploreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(place: Place)

    @Query("DELETE FROM places WHERE id =:id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM places")
    fun getPlaces(): Flow<List<Place>>

}