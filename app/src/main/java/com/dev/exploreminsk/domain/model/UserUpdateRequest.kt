package com.dev.exploreminsk.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateRequest(
    val email: String? = null,
    val username: String? = null,
    val byteArray: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserUpdateRequest

        if (email != other.email) return false
        if (username != other.username) return false
        if (!byteArray.contentEquals(other.byteArray)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = email?.hashCode() ?: 0
        result = 31 * result + (username?.hashCode() ?: 0)
        result = 31 * result + (byteArray?.contentHashCode() ?: 0)
        return result
    }
}