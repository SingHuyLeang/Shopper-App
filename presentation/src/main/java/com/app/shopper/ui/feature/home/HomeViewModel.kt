package com.app.shopper.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.model.Product
import com.app.domain.network.ResultWrapper
import com.app.domain.usecase.GetCategoryUseCase
import com.app.domain.usecase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
	private val getProductUseCase: GetProductUseCase,
	private val getCategoryUseCase: GetCategoryUseCase,
):ViewModel() {

	private val _uiState = MutableStateFlow<HomeScreenUIEvents> (HomeScreenUIEvents.Loading)
	val uiState = _uiState.asStateFlow()

	init {
        getAllProduct()
    }

	private fun getAllProduct() {
		viewModelScope.launch {
            _uiState.value = HomeScreenUIEvents.Loading
			val featureProducts = getProducts("electronics")
			val popularProducts = getProducts("jewelery")
			val categories = getCategories()
			if (featureProducts.isEmpty() && popularProducts.isEmpty() && categories.isEmpty()) {
				_uiState.value = HomeScreenUIEvents.Error("No products found")
				return@launch
			}
			_uiState.value = HomeScreenUIEvents.Success(featureProducts, popularProducts, categories)
        }
	}

	private suspend fun getProducts(category: String?): List<Product>{
		getProductUseCase.execute(category).let { result ->
			when (result) {
				is ResultWrapper.Success -> {
					return (result).value
				}
				is ResultWrapper.Failure ->{
					return emptyList()
				}
			}
		}
	}

	private suspend fun getCategories(): List<String>{
		getCategoryUseCase.execute().let {result ->
			when (result) {
				is ResultWrapper.Success -> {
					return (result).value
				}
				is ResultWrapper.Failure ->{
					return emptyList()
				}
			}
		}
	}
}
sealed class HomeScreenUIEvents {
	data object Loading: HomeScreenUIEvents()
	data class Success(val featured: List<Product>, val popularProduct: List<Product>,val categories: List<String>): HomeScreenUIEvents()
	data class Error(val message: String): HomeScreenUIEvents()
}