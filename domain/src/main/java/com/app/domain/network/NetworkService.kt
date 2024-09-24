package com.app.domain.network

import com.app.domain.model.CartItemModel
import com.app.domain.model.CartModel
import com.app.domain.model.CategoriesModel
import com.app.domain.model.ProductsModel
import com.app.domain.model.request.AddCartRequestModel

interface NetworkService {
	suspend fun getProducts(category: Int?): ResultWrapper<ProductsModel>
	suspend fun getCategories(): ResultWrapper<CategoriesModel>

	suspend fun addProductToCart(
		request: AddCartRequestModel
	): ResultWrapper<CartModel>

	suspend fun getCart(): ResultWrapper<CartModel>
}

sealed class ResultWrapper<out T> {
	data class Success<out T>(val value: T): ResultWrapper<T>()
	data class Failure(val exception: Exception): ResultWrapper<Nothing>()
}