package com.app.data.repository

import com.app.domain.model.Product
import com.app.domain.model.ProductsModel
import com.app.domain.network.NetworkService
import com.app.domain.network.ResultWrapper
import com.app.domain.repository.ProductRepository

class ProductRepositoryImpl(private val networkService: NetworkService) : ProductRepository {
	override suspend fun getProducts(category: Int?): ResultWrapper<ProductsModel> {
		return networkService.getProducts(category)
	}
}