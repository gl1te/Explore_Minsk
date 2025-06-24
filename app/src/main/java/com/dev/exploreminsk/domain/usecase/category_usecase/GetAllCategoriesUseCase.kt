package com.dev.exploreminsk.domain.usecase.category_usecase

import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.domain.repository.CategoryRepository
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(): Result<List<Category>>{
        return categoryRepository.getAllCategories()
    }

}