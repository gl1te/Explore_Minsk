package com.dev.exploreminsk.data.repository

import com.dev.exploreminsk.data.util.Constants.AVATARS_BUCKET
import com.dev.exploreminsk.data.util.Constants.AVATARS_BUCKET_PATH
import com.dev.exploreminsk.domain.repository.StorageRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
) : StorageRepository {

    override suspend fun uploadImage(
        userId: String,
        imageBytes: ByteArray,
    ): Result<String> {
        return try {
            val path = "$AVATARS_BUCKET_PATH + $userId.jpg"

            supabaseClient.storage.from(AVATARS_BUCKET).upload(
                path = path,
                data = imageBytes,
                options = {
                    upsert = true
                }
            )

            Result.success(path)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun downloadImage(path: String): Result<ByteArray> {
        return try {
            val bytes = supabaseClient.storage
                .from(AVATARS_BUCKET)
                .downloadAuthenticated(path)

            Result.success(bytes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}