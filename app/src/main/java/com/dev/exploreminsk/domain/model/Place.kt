package com.dev.exploreminsk.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.exploreminsk.data.util.Constants.ROOM_PLACES_NAME
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Entity(tableName = ROOM_PLACES_NAME)
@Parcelize
data class Place(
    @PrimaryKey val id: Long,
    val name: String,
    val rating: Float,
    val reviews: Int,
    val description: String,
    val imageLink: String,
    val sourceLink: String,
    val recommendedSightseeingTime: String? = null,
    val budget: String? = null,
    val openTime: String? = null,
    val address: String,
    val phone: String,
    val facilities: Map<String, Boolean>? = null,
    val category: Category
): Parcelable
