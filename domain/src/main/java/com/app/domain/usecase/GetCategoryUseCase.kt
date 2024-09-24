package com.app.domain.usecase

import com.app.domain.model.CategoriesModel
import com.app.domain.network.ResultWrapper
import com.app.domain.repository.CategoryRepository

class GetCategoryUseCase(private val repository: CategoryRepository) {
	suspend fun execute(): ResultWrapper<CategoriesModel> = repository.getCategories()
}