package com.dev.exploreminsk.data.repository

import com.dev.exploreminsk.domain.model.AuthResult
import com.dev.exploreminsk.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.exceptions.RestException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
) : AuthRepository {

    override suspend fun register(email: String, password: String): Result<AuthResult> {
        return try {
            supabaseClient.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            Result.success(AuthResult(true, message = "Registered successfully"))
        }
        catch (e: RestException) {
            Result.success(AuthResult(false, message = e.message, restResponse = e.response)) // <- вот это
        } catch (e: Exception) {
            Result.success(AuthResult(false, message = e.message, error = e))
        }
    }

    override suspend fun login(email: String, password: String): Result<AuthResult> {
        return try {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            val accessToken = supabaseClient.auth.currentAccessTokenOrNull() ?: ""
            Result.success(AuthResult(true, message = "Login successful", token = accessToken))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isLoggedIn(token: String): Result<AuthResult> {
        return try {
            if (token.isBlank()) {
                return Result.success(AuthResult(isSuccess = false, message = "No token found"))
            }
            val user = supabaseClient.auth.retrieveUser(token)
            if(user.email == null) {
                Result.success(AuthResult(isSuccess = false, message = "Invalid token"))
            } else {
                supabaseClient.auth.refreshCurrentSession()
                Result.success(AuthResult(isSuccess = true, message = "Session refreshed", token = token))
            }
        } catch (e: Exception) {
            Result.success(AuthResult(isSuccess = false, message = "Error checking login", error = e))
        }
    }
}
