package com.app.domain.network

import com.app.domain.model.Product

interface NetworkService {
	suspend fun getProducts(category: Int?): ResultWrapper<List<Product>>
	suspend fun getCategories(): ResultWrapper<List<String>>
}

sealed class ResultWrapper<out T> {
	data class Success<out T>(val value: T): ResultWrapper<T>()
	data class Failure(val exception: Exception): ResultWrapper<Nothing>()
}