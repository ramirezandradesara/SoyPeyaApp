package com.soyhenryfeature.products.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soyhenryfeature.products.viewmodel.ProductsViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import com.soyhenry.core.state.UiState
import com.soyhenry.feature.cart.viewmodel.CartViewModel
import com.soyhenry.library.ui.components.EmptyState

@Composable
fun ProductsView(
    navController: NavController,
    viewModel: ProductsViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val filterText by viewModel.filterText.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        ProductListHeader(
            filterText = filterText,
            onFilterTextChange = viewModel::onFilterTextChange
        )

        when (val state = uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                val products = state.data
                if (products.isEmpty()) {
                    EmptyState(
                        title = "No products found",
                        subtitle = "Try adjusting your filters.",
                        icon = Icons.Default.Search,
                    )
                } else {
                    ProductList(
                        products = products,
                        onAddToCart = cartViewModel::addToCart
                    )
                }
            }

            is UiState.Error -> {
                Text(text = state.message)
            }
        }
    }
}