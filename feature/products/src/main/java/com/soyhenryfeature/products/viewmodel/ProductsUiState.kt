package com.soyhenryfeature.products.viewmodel

import com.soyhenry.core.model.database.entities.ProductEntity

sealed class ProductsUiState {
    object Loading : ProductsUiState()
    data class Success(val products: List<ProductEntity>) : ProductsUiState()
    data class Error(val message: String) : ProductsUiState()
}