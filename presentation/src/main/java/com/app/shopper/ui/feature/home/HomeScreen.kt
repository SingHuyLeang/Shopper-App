package com.app.shopper.ui.feature.home

import android.R.bool
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.domain.model.Product
import com.app.shopper.R
import com.app.shopper.model.UIProductModel
import com.app.shopper.navigation.ProductDetails
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController,viewModel: HomeViewModel = koinViewModel()) {
	val uiState = viewModel.uiState.collectAsState()

	val loading = remember {
		mutableStateOf(false)
	}

	val errorMsg = remember {
		mutableStateOf<String?>(null)
	}

	val featured = remember {
		mutableStateOf<List<Product>>(emptyList())
	}

	val categories = remember {
		mutableStateOf<List<String>>(emptyList())
	}

	val popular = remember {
		mutableStateOf<List<Product>>(emptyList())
	}

	Scaffold { it ->
		Surface (modifier = Modifier
			.fillMaxSize()
			.padding(it)) {

			when (uiState.value) {
				is HomeScreenUIEvents.Loading -> {
					loading.value = true
					errorMsg.value = null
				}
				is HomeScreenUIEvents.Success -> {
					val data = (uiState.value as HomeScreenUIEvents.Success)
					loading.value = false
					errorMsg.value = null
					featured.value = data.featured
					popular.value = data.popularProduct
					categories.value = data.categories
				}
				is HomeScreenUIEvents.Error -> {
					errorMsg.value = (uiState.value as HomeScreenUIEvents.Error).message
					loading.value = false
					errorMsg.value = null
				}
			}
			
			HomeContent(
				featured = featured.value,
				popularProducts = popular.value,
				categories = categories.value,
				isLoading = loading.value,
				errorMsg = errorMsg.value,
				onClick = {product ->
					navController.navigate(ProductDetails(UIProductModel.fromProduct(product)))
				}
			)
			
		}
	}
}

@Composable
fun HomeContent(
	featured: List<Product>,
	popularProducts: List<Product>,
	categories: List<String>,
	isLoading: Boolean = false,
	errorMsg: String? = null,
	onClick: (Product)-> Unit
) {
	LazyColumn {
		item {
			ProfileHeader()
			Spacer(modifier = Modifier.size(16.dp))
			SearchBar(value = "", onChange = {})
			Spacer(modifier = Modifier.size(16.dp))
		}

		item {
			if (isLoading) {
				Column(
					modifier = Modifier.fillMaxSize(),
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.CenterHorizontally,
				) {
					Spacer(modifier = Modifier.height(32.dp))
					CircularProgressIndicator(
						modifier = Modifier.size(30.dp)
					)
					Spacer(modifier = Modifier.height(16.dp))
					Text(
						text = "Waiting...",
						style = MaterialTheme.typography.bodyMedium,
					)
				}
			}

			errorMsg?.let {
				Text(
					text = it,
					style = MaterialTheme.typography.bodyMedium,
				)
			}

			if (categories.isNotEmpty()) {
				LazyRow {
					items(categories, key = {it}) { category ->

						val isVisible = remember {
							mutableStateOf(false)
						}
						LaunchedEffect(true) {
							isVisible.value = true
						}
						AnimatedVisibility(visible = isVisible.value, enter = fadeIn() + expandVertically()) {
							Text(
								text = category.apply { replaceFirstChar { it.uppercaseChar() } },
								style = MaterialTheme.typography.bodyMedium,
								color = MaterialTheme.colorScheme.onPrimary,
								modifier = Modifier
									.padding(horizontal = 8.dp)
									.clip(RoundedCornerShape(8.dp))
									.background(MaterialTheme.colorScheme.primary)
									.padding(vertical = 8.dp, horizontal = 16.dp)
							)
						}
					}
				}
				Spacer(modifier = Modifier.size(24.dp))
			}
			if (featured.isNotEmpty()) {
				HomeProductRow(product = featured, title = "Featured", onClick = onClick)
				Spacer(modifier = Modifier.size(24.dp))
			}
			if (popularProducts.isNotEmpty()) {
				HomeProductRow(product = popularProducts, title = "Popular Products", onClick = onClick)
				Spacer(modifier = Modifier.size(24.dp))
			}
		}
	}
}

@Composable
fun ProfileHeader() {
	Box (
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 16.dp, horizontal = 8.dp),
	) {
		Row (
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.align(Alignment.CenterStart)
		) {
			Image(
				modifier = Modifier
					.size(48.dp)
					.clip(shape = RoundedCornerShape(50)),
				painter = painterResource(id = R.drawable.profile),
				contentDescription = null,
			)
			Spacer(modifier = Modifier.width(8.dp))
			Column {
				Text(text = "Hello, ", style = MaterialTheme.typography.bodySmall)
				Text(
					text = "John Doe",
					style = MaterialTheme.typography.bodyMedium,
					fontWeight = FontWeight.SemiBold,
				)
			}
		}
		Image(
			painter = painterResource(
				id = R.drawable.notification
			),
			contentDescription = null,
			modifier = Modifier
				.size(48.dp)
				.padding(8.dp)
				.align(Alignment.CenterEnd)
				.clip(CircleShape)
				.background(Color.LightGray.copy(alpha = 0.3f)),
			contentScale = ContentScale.Inside,
		)
	}
}

@Composable
fun SearchBar(value: String, onChange: (String) -> Unit) {
	TextField(
		value = value,
		onValueChange = onChange,
		modifier = Modifier
			.padding(horizontal = 16.dp)
			.fillMaxWidth(),
		shape = RoundedCornerShape(32.dp),
		leadingIcon = {
			Image(
				painter = painterResource(id = R.drawable.search),
				contentDescription = null,
				modifier = Modifier.size(24.dp),
			)
		},
		colors = TextFieldDefaults.colors(
			focusedIndicatorColor = Color.Transparent,
			unfocusedIndicatorColor = Color.Transparent,
			focusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
			unfocusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
		),
		placeholder = {
			Text(
				text = "Search for products",
				style = MaterialTheme.typography.bodySmall,
			)
		},
	)
}

@Composable
fun HomeProductRow(product: List<Product>, title: String, onClick: (Product)-> Unit) {
	Column {
		Box (modifier = Modifier
			.padding(vertical = 24.dp, horizontal = 8.dp)
			.fillMaxWidth()) {
			Text(
				text = title,
				style = MaterialTheme.typography.titleMedium,
				modifier = Modifier.align(Alignment.CenterStart),
				fontWeight = FontWeight.SemiBold,
			)
			Text(
				text = "View all",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.primary,
				modifier = Modifier.align(Alignment.CenterEnd),
			)
		}
		LazyRow {
			items(product, key = {it.id}){ product ->
				val isVisible = remember {
					mutableStateOf(false)
				}
				LaunchedEffect(true) {
					isVisible.value = true
				}
				AnimatedVisibility(visible = isVisible.value, enter = fadeIn() + expandVertically()) {
					ProductItem(product = product, onClick = onClick)
				}
			}
		}
	}
	
}

@Composable
fun ProductItem(product: Product, onClick: (Product)-> Unit) {
	Card(
		modifier = Modifier
			.padding(horizontal = 8.dp)
			.size(
				width = 126.dp,
				height = 164.dp,
			).clickable {
				onClick(product)
			},
		shape = RoundedCornerShape(16.dp),
		colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.3f)),
	) {
		Column(modifier = Modifier.fillMaxSize()){
			AsyncImage(
				model = product.image,
				contentDescription = null,
				modifier = Modifier
					.fillMaxWidth()
					.height(96.dp),
				placeholder = painterResource(id = R.drawable.no_data),
				error = painterResource(id = R.drawable.no_data),
				contentScale = ContentScale.Inside,
			)
			Spacer(modifier = Modifier.padding(8.dp))
			Text(
				text = product.title,
				style = MaterialTheme.typography.bodyMedium,
				modifier = Modifier.padding(horizontal = 8.dp),
				fontWeight = FontWeight.SemiBold,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis,
			)
			Text(
				text = product.priceString,
				style = MaterialTheme.typography.bodySmall,
				modifier = Modifier.padding(horizontal = 8.dp),
				color = MaterialTheme.colorScheme.primary,
				fontWeight = FontWeight.SemiBold,
			)
		}
	}
}
