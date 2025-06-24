package com.dev.exploreminsk.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomSQLiteQuery
import androidx.room.TypeConverters
import com.dev.exploreminsk.domain.model.Place

@Database(entities = [Place::class], version = 3)
@TypeConverters(TypeConvertor::class)
abstract class ExploreDatabase: RoomDatabase() {
    abstract val exploreDao: ExploreDao
}