package com.app.domain.repository

import com.app.domain.network.ResultWrapper

interface CategoryRepository {
	suspend fun getCategories(): ResultWrapper<List<String>>
}