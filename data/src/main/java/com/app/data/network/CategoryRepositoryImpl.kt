package com.app.data.network

import com.app.domain.network.NetworkService
import com.app.domain.network.ResultWrapper
import com.app.domain.repository.CategoryRepository

class CategoryRepositoryImpl(private val networkService: NetworkService): CategoryRepository {
	override suspend fun getCategories(): ResultWrapper<List<String>> {
		return networkService.getCategories()
	}
}