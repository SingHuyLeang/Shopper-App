package com.app.data.model.response

import com.app.domain.model.CartModel
import kotlinx.serialization.Serializable

@Serializable
data class CartResponse (
	val data: List<CartItem>,
	val msg: String,
) {
	fun toCartModel(): CartModel = CartModel(
		data = data.map { it.toCartItemModel() },
		msg = msg,
	)

}