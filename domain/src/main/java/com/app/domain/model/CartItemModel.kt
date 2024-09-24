package com.app.domain.model

data class CartItemModel(
	val id: Int,
	val productId: Int,
	val userId: Int,
	val name: String,
	val price: Double,
	val imageURL: String,
	val quantity: Int,
	val productName: String,
)
