package com.app.data.model

import com.app.domain.model.Category
import kotlinx.serialization.Serializable

@Serializable
data class DataCategoriesModel(
    val id: Int,
    val image: String,
    val title: String
) {
    fun toCategory() = Category(
        id = id,
        image = image,
        title = title,
    )
}