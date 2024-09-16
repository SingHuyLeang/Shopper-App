package com.app.shopper

import android.app.Application
import com.app.data.di.dataModule
import com.app.domain.di.domainModule
import com.app.shopper.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShopperApp : Application() {
	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidContext(this@ShopperApp)
			modules(listOf(
				presentationModule,
				dataModule,
				domainModule,
			))
		}
	}
}