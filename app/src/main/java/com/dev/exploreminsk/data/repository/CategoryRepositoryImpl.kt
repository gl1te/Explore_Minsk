package com.dev.exploreminsk.data.repository

import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.domain.repository.CategoryRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
) : CategoryRepository {

    override suspend fun getAllCategories(): Result<List<Category>> {
        return try {
            val categories = supabaseClient.postgrest["categories"].select().decodeList<Category>()
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCategory(id: Long): Result<Category?> {
        return try {
            val category = supabaseClient.postgrest["categories"]
                .select {
                    filter {
                        eq("id", id)
                    }
                    single()
                }.decodeAs<Category>()
            Result.success(category)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}