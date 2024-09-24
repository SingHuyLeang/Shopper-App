package com.app.domain.repository

import com.app.domain.model.CartItemModel
import com.app.domain.model.CartModel
import com.app.domain.model.request.AddCartRequestModel
import com.app.domain.network.ResultWrapper

interface CartRepository {
	suspend fun addProductToCart(
		request: AddCartRequestModel
	): ResultWrapper<CartModel>
	suspend fun getCart(): ResultWrapper<CartModel>
}