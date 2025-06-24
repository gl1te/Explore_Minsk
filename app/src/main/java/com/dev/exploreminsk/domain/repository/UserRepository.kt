package com.dev.exploreminsk.domain.repository

import com.dev.exploreminsk.domain.model.User
import com.dev.exploreminsk.domain.model.UserUpdateRequest

interface UserRepository {
    suspend fun getUser(): Result<User>
    suspend fun updateUser(userUpdateRequest: UserUpdateRequest): Result<Boolean>
}