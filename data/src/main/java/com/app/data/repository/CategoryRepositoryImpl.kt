package com.app.data.repository

import com.app.domain.model.CategoriesModel
import com.app.domain.network.NetworkService
import com.app.domain.network.ResultWrapper
import com.app.domain.repository.CategoryRepository

class CategoryRepositoryImpl(private val networkService: NetworkService): CategoryRepository {
	override suspend fun getCategories(): ResultWrapper<CategoriesModel> {
		return networkService.getCategories()
	}
}