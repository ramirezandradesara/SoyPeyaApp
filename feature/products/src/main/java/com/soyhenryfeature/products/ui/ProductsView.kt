package com.soyhenryfeature.products.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soyhenryfeature.products.viewmodel.ProductsViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.LaunchedEffect
import com.soyhenry.core.state.UiState
import com.soyhenry.feature.cart.viewmodel.CartViewModel
import com.soyhenry.library.ui.components.EmptyState
import com.soyhenry.library.ui.components.container.ViewContainer
import androidx.compose.ui.res.stringResource
import com.soyhenryfeature.products.R

@Composable
fun ProductsView(
    navController: NavController,
    viewModel: ProductsViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val filterText by viewModel.filterText.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadProducts(refreshData = true)
    }

    ViewContainer(title = stringResource(R.string.products_title)) {
        OutlinedTextField(
            value = filterText,
            onValueChange = viewModel::onFilterTextChange,
            label = { Text(stringResource(R.string.filter_products_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        when (val state = uiState) {
            is UiState.Loading -> {
                ProductsSkeletonList()
            }

            is UiState.Success -> {
                val products = state.data
                if (products.isEmpty()) {
                    EmptyState(
                        title = stringResource(R.string.no_products_title),
                        subtitle = stringResource(R.string.no_products_subtitle),
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