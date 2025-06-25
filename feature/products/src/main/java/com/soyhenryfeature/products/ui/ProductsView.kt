package com.soyhenryfeature.products.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soyhenryfeature.products.viewmodel.ProductsUiState
import com.soyhenryfeature.products.viewmodel.ProductsViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding

@Composable
fun ProductsView(
    navController: NavController,
    viewModel: ProductsViewModel = hiltViewModel()
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
            is ProductsUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is ProductsUiState.Error -> {
                Text("Error: ${state.message}")
            }

            is ProductsUiState.Success -> {
                ProductList(products = state.products)
            }
        }
    }
}