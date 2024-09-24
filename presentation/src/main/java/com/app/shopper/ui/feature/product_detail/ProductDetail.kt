package com.app.shopper.ui.feature.product_detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.shopper.R
import com.app.shopper.model.UIProductModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductDetailScreen(
	navController: NavController,
	product: UIProductModel,
	viewModel: ProductDetailViewModel = koinViewModel(),
) {
	Column (
		modifier = Modifier
			.fillMaxSize()
			.verticalScroll(rememberScrollState())
	) {
		Box(modifier = Modifier.weight(1f)) {
			AsyncImage(
				model = product.image,
				contentDescription = product.title,
				contentScale = ContentScale.Fit,
				modifier = Modifier.fillMaxSize()
			)
			Image(
				painter = painterResource(id = R.drawable.arrow_back),
				contentDescription = "Back",
				modifier = Modifier
					.padding(16.dp)
					.size(48.dp)
					.clip(CircleShape)
					.background(Color.LightGray.copy(alpha = 0.4f))
					.padding(8.dp)
					.align(Alignment.TopStart)
			)
			Image(
				painter = painterResource(id = R.drawable.heart),
				contentDescription = "Favorite",
				modifier = Modifier
					.padding(16.dp)
					.size(48.dp)
					.clip(CircleShape)
					.background(Color.LightGray.copy(alpha = 0.4f))
					.padding(8.dp)
					.align(Alignment.TopEnd)
			)
		}
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 16.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			Image(
				painter = painterResource(id = R.drawable.ic_star),
				contentDescription = "Rating",
			)
			Spacer(modifier = Modifier.size(4.dp))
			Text(
				text = "4.5",
				style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
			)
			Spacer(modifier = Modifier.size(16.dp))
			Text(
				text = "(20 Reviews)",
				style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
				color = Color.Gray,
			)
		}
		Spacer(modifier = Modifier.size(16.dp))
		Text(
			text = "Description",
			style = MaterialTheme.typography.titleMedium,
			modifier = Modifier.padding(start = 16.dp),
		)
		Spacer(modifier = Modifier.size(10.dp))
		Text(
			text = product.description,
			style = MaterialTheme.typography.titleMedium,
			maxLines = 3,
			modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
		)
		Spacer(modifier = Modifier.size(16.dp))
		Text(
			text = "Size",
			style = MaterialTheme.typography.titleMedium,
			modifier = Modifier.padding(start = 16.dp),
		)
		Spacer(modifier = Modifier.size(10.dp))
		Row (modifier = Modifier.padding(horizontal = 16.dp)){
			repeat(4) {
				SizeItem(size = "${it + 1}", isSelected = it == 0) {}
			}
		}
		Spacer(modifier = Modifier.size(16.dp))
		Row (
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 16.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			Button(
				modifier = Modifier.weight(1f),
				onClick = { viewModel.addProductToCart(product) }
			) {
				Text(text = "Buy Now")
			}
			Spacer(modifier = Modifier.size(8.dp))
			IconButton(
				onClick = { viewModel.addProductToCart(product) },
				Modifier.padding(horizontal = 16.dp),
				colors = IconButtonDefaults.iconButtonColors().copy(
					containerColor = Color.LightGray.copy(alpha = 0.4f),
				)
			) {
				Image(
					painter = painterResource(id = R.drawable.ic_cart),
					contentDescription = "Cart",
				)
			}
		}
	}
	Box (modifier = Modifier.fillMaxSize()) {
		val uiState = viewModel.state.collectAsState()
		val loading = remember {
			mutableStateOf(false)
		}
		LaunchedEffect(uiState.value) {
			when (uiState.value) {
				is ProductDetailEvent.Loading -> loading.value = true
				is ProductDetailEvent.Success -> {
					loading.value = false
					Toast.makeText(
						navController.context,
						(uiState.value as ProductDetailEvent.Success).message,
						Toast.LENGTH_SHORT,
					).show()
				}
				is ProductDetailEvent.Error -> {
					Toast.makeText(
						navController.context,
						(uiState.value as ProductDetailEvent.Error).message,
						Toast.LENGTH_SHORT,
					).show()
					loading.value = false
				}
				else -> loading.value = false
			}
		}
		if (loading.value) {
			Column (
				modifier = Modifier
					.fillMaxSize()
					.background(Color.Black.copy(alpha = 0.7f)),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.Center
			) {
				CircularProgressIndicator(modifier = Modifier.size(34.dp))
				Text(
					text = "Adding to cart...",
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onPrimary
				)
			}
		}
	}
}


@Composable
fun SizeItem(size: String, isSelected: Boolean, onClick: ()-> Unit) {
	Box (
		modifier = Modifier
			.padding(horizontal = 4.dp)
			.size(48.dp)
			.clip(RoundedCornerShape(8.dp))
			.border(
				width = 1.dp,
				color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
				shape = RoundedCornerShape(8.dp)
			)
			.background(
				color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
			)
			.clickable { onClick() },
	) {
		Text(
			text = size,
			color = if (isSelected) Color.White else Color.DarkGray,
			style = MaterialTheme.typography.bodySmall,
			modifier = Modifier.align(Alignment.Center).padding(8.dp)
		)
	}
}