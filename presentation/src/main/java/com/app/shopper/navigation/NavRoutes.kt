package com.app.shopper.navigation

import com.app.shopper.model.UIProductModel
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
object CartScreen

@Serializable
object ProfileScreen

@Serializable
data class ProductDetails(val product: UIProductModel)