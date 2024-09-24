package com.app.data.model.response

import com.app.domain.model.CartItemModel
import com.app.domain.model.CartModel
import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
	val id: Int,
	val productId: Int,
	val userId: Int,
	val name: String,
	val price: Double,
	val imageURL: String,
	val quantity: Int,
	val productName: String,
){
	fun toCartItemModel(): CartItemModel = CartItemModel(
		id = id,
		productId = productId,
		userId = userId,
		name = name,
		price = price,
		imageURL = imageURL,
		quantity = quantity,
		productName = productName,
	)
}