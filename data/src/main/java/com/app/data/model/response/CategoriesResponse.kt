package com.app.data.model.response

import com.app.data.model.DataCategoriesModel
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponse(
	val `data`: List<DataCategoriesModel>,
	val msg: String
) {
	fun toCategories() = com.app.domain.model.CategoriesModel(
		categories = `data`.map { it.toCategory() },
		msg = msg
	)
}