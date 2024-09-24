package com.app.data.di

import com.app.data.repository.CategoryRepositoryImpl
import com.app.data.repository.ProductRepositoryImpl
import com.app.domain.repository.CategoryRepository
import com.app.domain.repository.ProductRepository
import org.koin.dsl.module

val repositoryModule = module {
	single <ProductRepository> { ProductRepositoryImpl(get()) }
	single <CategoryRepository> { CategoryRepositoryImpl(get()) }
}