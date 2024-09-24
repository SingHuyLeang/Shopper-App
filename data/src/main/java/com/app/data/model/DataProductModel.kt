package com.app.data.model

import com.app.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
class DataProductModel(
	private val categoryId: Int,
	private val description: String,
	private val id: Int,
	private val image: String,
	private val price: Double,
	private val title: String
) {
	fun toProduct() = Product(
		id = id,
		title = title,
		price = price,
		categoryId = categoryId,
		description = description,
		image = image,
	)
}
