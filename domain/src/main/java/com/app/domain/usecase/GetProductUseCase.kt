package com.app.domain.usecase

import com.app.domain.repository.ProductRepository

class GetProductUseCase(private val repository: ProductRepository) {
	suspend fun execute(category: Int?) = repository.getProducts(category)
}