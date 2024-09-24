package com.app.shopper.ui.feature.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.model.Product
import com.app.domain.model.request.AddCartRequestModel
import com.app.domain.network.ResultWrapper
import com.app.domain.usecase.AddProductUseCase
import com.app.shopper.model.UIProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val useCase: AddProductUseCase):ViewModel() {
	private val _state = MutableStateFlow<ProductDetailEvent>(ProductDetailEvent.Nothing)
	val state = _state.asStateFlow()

	fun addProductToCart(product: UIProductModel){
		viewModelScope.launch {
			_state.value = ProductDetailEvent.Loading
			val result = useCase.execute(
				AddCartRequestModel(
					productId = product.id,
					productName = product.title,
					price = product.price,
					quantity = 1,
					userId = 1
				)
			)
			when(result){
				is ResultWrapper.Success -> _state.value = ProductDetailEvent.Success("Product add to cart success")
				is ResultWrapper.Failure -> _state.value = ProductDetailEvent.Error(result.exception.message ?: "Unknown error")
			}
		}
	}

}
sealed class ProductDetailEvent{
	data object Loading: ProductDetailEvent()
	data object Nothing: ProductDetailEvent()
	data class Error(val message: String): ProductDetailEvent()
	data class Success(val message: String): ProductDetailEvent()
}