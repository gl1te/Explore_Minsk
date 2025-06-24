package com.dev.exploreminsk.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Category(
    val id: Long,
    var name: String
): Parcelable
