package com.dev.exploreminsk.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val email: String,
    val username: String? = null,
    val imageUrl: String? = null
)
