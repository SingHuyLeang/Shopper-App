package com.app.data.model.response

import com.app.data.model.DataProductModel
import com.app.domain.model.ProductsModel
import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponse(
    val `data`: List<DataProductModel>,
    val msg: String
) {
    fun toProducts() = ProductsModel(
        products = data.map { it.toProduct() },
        msg = msg,
    )
}