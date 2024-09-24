package com.app.shopper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.app.domain.model.Product
import com.app.shopper.model.UIProductModel
import com.app.shopper.navigation.CartScreen
import com.app.shopper.navigation.HomeScreen
import com.app.shopper.navigation.ProductDetails
import com.app.shopper.navigation.ProfileScreen
import com.app.shopper.navigation.productNavType
import com.app.shopper.ui.feature.home.HomeScreen
import com.app.shopper.ui.feature.product_detail.ProductDetailScreen
import com.app.shopper.ui.theme.ShopperTheme
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			ShopperTheme {
				val showBottomNavBar = remember{
					mutableStateOf(true)
				}
				val navController = rememberNavController()
				Scaffold (
					modifier = Modifier.fillMaxSize(),
					bottomBar = {
						AnimatedVisibility(visible = showBottomNavBar.value, enter = fadeIn()) {
							BottomNavigationBar(navController = navController)
						}
					}
				) { pv ->
					Surface (
						modifier = Modifier
							.fillMaxSize()
							.padding(pv)
					) {
						NavHost(navController = navController, startDestination = HomeScreen) {
							composable <HomeScreen> {
								HomeScreen(navController)
								showBottomNavBar.value = true
							}
							composable <CartScreen> {
								Box (
									modifier = Modifier.fillMaxSize(),
									contentAlignment = Alignment.Center,
								) {
									Text(text = "Cart", style = MaterialTheme.typography.bodyMedium)
								}
								showBottomNavBar.value = true
							}
							composable <ProfileScreen> {
								Box (
									modifier = Modifier.fillMaxSize(),
									contentAlignment = Alignment.Center,
								) {
									Text(text = "Profile", style = MaterialTheme.typography.bodyMedium)
								}
								showBottomNavBar.value = true
							}
							composable <ProductDetails> (
								typeMap = mapOf(typeOf<UIProductModel>() to productNavType)
							) {
								val productRoute = it.toRoute<ProductDetails>()
								ProductDetailScreen(navController = navController, product = productRoute.product)
								showBottomNavBar.value = false
							}
						}
					}
				}
			}
		}
	}
}


@Composable
fun BottomNavigationBar(navController: NavController){
	NavigationBar {
		val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
		val items = listOf(
			BottomNavItem.Home,
			BottomNavItem.Cart,
			BottomNavItem.Profile,
		)

		items.forEach {item ->

			val isSelected = currentRoute?.substringBefore("?") == item.route::class.qualifiedName

			NavigationBarItem(
				selected = isSelected,
				onClick = {
					navController.navigate(item.route) {
						navController.graph.startDestinationRoute?.let {startRoute ->
							popUpTo(startRoute){
								saveState = true
							}
						}
						launchSingleTop = true
						restoreState = true
					}
				},
				label = { Text(text = item.title)},
				icon = {
					Image(
						painter = painterResource(id = item.icon),
						contentDescription = item.title,
						colorFilter = ColorFilter.tint(
							if (isSelected)
								MaterialTheme.colorScheme.primary
							else
								Color.Gray
						)
					)
				},
				colors = NavigationBarItemDefaults.colors().copy(
					selectedIconColor = MaterialTheme.colorScheme.primary,
					selectedTextColor = MaterialTheme.colorScheme.primary,
					unselectedIconColor = Color.Gray,
					unselectedTextColor = Color.Gray,
				)
			)
		}

	}
}

sealed class BottomNavItem(val route: Any, val title: String, val icon: Int) {
	data object Home: BottomNavItem(route = HomeScreen, title = "Home", icon = R.drawable.ic_home)
	data object Cart: BottomNavItem(route = CartScreen,title = "Cart",icon = R.drawable.ic_cart)
	data object Profile: BottomNavItem(route = ProfileScreen,title = "Profile",icon = R.drawable.ic_profile)
//	data object Home: BottomNavItem(route = "home","Home")
}