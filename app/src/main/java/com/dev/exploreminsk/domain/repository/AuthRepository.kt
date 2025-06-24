package com.dev.exploreminsk.domain.repository

import com.dev.exploreminsk.domain.model.AuthResult

interface AuthRepository {
    suspend fun register(email: String, password: String): Result<AuthResult>
    suspend fun login(email: String, password: String): Result<AuthResult>
    suspend fun isLoggedIn(token: String): Result<AuthResult>
}