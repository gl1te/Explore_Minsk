package com.dev.exploreminsk.domain.repository

interface StorageRepository {
    suspend fun uploadImage(userId: String, imageBytes: ByteArray): Result<String>
    suspend fun downloadImage(path: String): Result<ByteArray>
}