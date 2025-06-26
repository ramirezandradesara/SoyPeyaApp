package com.soyhenry.feature.cart.viewmodel

import com.soyhenry.core.model.CartItem

data class CartUiState(
    val isLoading: Boolean = true,
    val cart: List<CartItem> = emptyList(),
    val error: Exception? = null
)