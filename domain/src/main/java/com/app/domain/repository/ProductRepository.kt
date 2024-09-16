package com.app.domain.repository

import com.app.domain.model.Product
import com.app.domain.network.ResultWrapper

interface ProductRepository {
	suspend fun getProducts(category: Int?): ResultWrapper<List<Product>>
}