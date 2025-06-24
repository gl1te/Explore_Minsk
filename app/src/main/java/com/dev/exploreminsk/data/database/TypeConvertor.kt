package com.dev.exploreminsk.data.database

import androidx.room.TypeConverter
import com.dev.exploreminsk.domain.model.Category
import kotlinx.serialization.json.Json

class TypeConvertor {
    @TypeConverter
    fun fromMap(map: Map<String, Boolean>?): String? {
        return map?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toMap(json: String?): Map<String, Boolean>? {
        return json?.let { Json.decodeFromString(it) }
    }

    @TypeConverter
    fun fromCategory(category: Category?): String? {
        return category?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toCategory(json: String?): Category? {
        return json?.let { Json.decodeFromString(it) }
    }
}