package com.app.shopper.di

import com.app.shopper.ui.feature.home.HomeViewModel
import com.app.shopper.ui.feature.product_detail.ProductDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
	viewModel {
		HomeViewModel(get(),get())
	}
	viewModel {
		ProductDetailViewModel(get())
	}
}