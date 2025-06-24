package com.dev.exploreminsk.data.dto

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.exploreminsk.data.util.Constants.ROOM_PLACES_NAME
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class PlaceDto(
    val id: Long,
    val name: String,
    val rating: Float,
    val reviews: Int,
    val description: String,
    val image_link: String,
    val source_link: String,
    val recommended_sightseeing_time: String? = null,
    val budget: String? = null,
    val open_time: String? = null,
    val address: String,
    val phone: String,
    val facilities: Map<String, Boolean>? = null,
    val category_id: Long
)
