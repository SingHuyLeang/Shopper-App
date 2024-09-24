package com.app.domain.usecase

import com.app.domain.model.CartModel
import com.app.domain.model.request.AddCartRequestModel
import com.app.domain.network.ResultWrapper
import com.app.domain.repository.CartRepository

class AddProductUseCase(private val cartRepository: CartRepository) {
	suspend fun execute(request: AddCartRequestModel): ResultWrapper<CartModel> = cartRepository.addProductToCart(request)
}