package com.app.data.repository

import com.app.domain.model.CartItemModel
import com.app.domain.model.CartModel
import com.app.domain.model.request.AddCartRequestModel
import com.app.domain.network.NetworkService
import com.app.domain.network.ResultWrapper
import com.app.domain.repository.CartRepository

class CartRepositoryImpl(private val networkService: NetworkService): CartRepository {
	override suspend fun addProductToCart(request: AddCartRequestModel): ResultWrapper<CartModel> {
		return networkService.addProductToCart(request);
	}

	override suspend fun getCart(): ResultWrapper<CartModel> {
		return networkService.getCart();
	}
}