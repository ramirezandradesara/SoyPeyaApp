package com.soyhenryfeature.products.viewmodel

import com.soyhenryfeature.products.data.model.Product

sealed class ProductsUiState {
    object Loading : ProductsUiState()
    data class Success(val products: List<Product>) : ProductsUiState()
    data class Error(val message: String) : ProductsUiState()
}