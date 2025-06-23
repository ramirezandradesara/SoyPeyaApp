package com.soyhenryfeature.products.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.soyhenryfeature.products.data.model.Product
import com.soyhenryfeature.products.viewmodel.ProductsUiState
import com.soyhenryfeature.products.viewmodel.ProductsViewModel
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun ProductsView(
    navController: NavController,
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is ProductsUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ProductsUiState.Error -> {
            Text("Error: ${state.message}")
        }
        is ProductsUiState.Success -> {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.products) { product ->
                    ProductItem(product = product)
                }
            }
        }
    }

    /* when (val state = uiState) {  // Note the 'val' here
        is ProductsUiState.Loading -> LoadingScreen()
        is ProductsUiState.Success -> ProductList(state.products)
        is ProductsUiState.Error -> ErrorScreen(state.message)
    } */
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

