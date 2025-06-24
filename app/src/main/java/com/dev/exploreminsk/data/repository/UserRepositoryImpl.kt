package com.dev.exploreminsk.data.repository

import com.dev.exploreminsk.domain.model.User
import com.dev.exploreminsk.domain.model.UserUpdateRequest
import com.dev.exploreminsk.domain.repository.StorageRepository
import com.dev.exploreminsk.domain.repository.UserRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.storage.storage
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

class UserRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val storageRepository: StorageRepository,
) : UserRepository {

    override suspend fun getUser(): Result<User> {
        return try {
            val currentUser = supabaseClient.auth.currentUserOrNull()
                ?: throw IllegalStateException("User not authenticated")
            val username = currentUser.userMetadata?.get("username")?.jsonPrimitive?.content
            val imagePath = currentUser.userMetadata?.get("image_path")?.jsonPrimitive?.content

            val signedUrl = imagePath?.let {
                supabaseClient.storage.from("avatars").createSignedUrl(
                    path = it,
                    expiresIn = 1.hours
                )
            }

            val user = User(
                id = currentUser.id,
                email = currentUser.email ?: "",
                username = username,
                imageUrl = signedUrl
            )

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(userUpdateRequest: UserUpdateRequest): Result<Boolean> {
        return try {
            val currentUser = supabaseClient.auth.currentUserOrNull()
                ?: throw IllegalStateException("User not authenticated")

            var imagePath: String? = null
            if (userUpdateRequest.byteArray != null) {
                val result = storageRepository.uploadImage(
                    userId = currentUser.id,
                    imageBytes = userUpdateRequest.byteArray
                )
                if (result.isSuccess) {
                    imagePath = result.getOrNull()
                } else {
                    throw IllegalStateException("Image upload failed: ${result.exceptionOrNull()}")
                }
            }

            supabaseClient.auth.updateUser {
                userUpdateRequest.email?.let { email = it }
                data = buildJsonObject {
                    put("username", JsonPrimitive(userUpdateRequest.username))
                    imagePath?.let { put("image_path", JsonPrimitive(it)) }
                }
            }
            Result.success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}

