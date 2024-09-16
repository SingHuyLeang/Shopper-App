package com.app.domain.di

import com.app.domain.usecase.GetCategoryUseCase
import com.app.domain.usecase.GetProductUseCase
import org.koin.dsl.module

val useCaseModule = module {
	factory { GetProductUseCase(get()) }
	factory { GetCategoryUseCase(get()) }
}