package com.dev.exploreminsk.domain.repository

import com.dev.exploreminsk.domain.model.Category

interface CategoryRepository {
    suspend fun getAllCategories(): Result<List<Category>>
    suspend fun getCategory(id: Long): Result<Category?>
}