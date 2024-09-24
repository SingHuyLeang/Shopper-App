package com.app.domain.repository

import com.app.domain.model.CategoriesModel
import com.app.domain.network.ResultWrapper

interface CategoryRepository {
	suspend fun getCategories(): ResultWrapper<CategoriesModel>
}