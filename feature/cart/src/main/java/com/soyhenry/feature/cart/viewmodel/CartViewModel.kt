package com.soyhenry.feature.cart.viewmodel

import androidx.lifecycle.ViewModel
import com.soyhenry.core.model.CartItem
import com.soyhenry.core.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addToCart(product: Product) {
        _cartItems.update { currentItems ->
            val existingItem = currentItems.find { it.product.id == product.id }

            if (existingItem != null) {
                currentItems.map {
                    if (it.product.id == product.id)  {
                        it.copy(quantity = it.quantity + 1)
                    }
                    else {
                        it
                    }
                }
            } else {
                currentItems + CartItem(product)
            }
        }
    }

    fun updateQuantity(productId: Int, newQuantity: Int) {
        _cartItems.update { items ->
            items.map {
                if (it.product.id == productId) it.copy(quantity = newQuantity.coerceAtLeast(1))
                else it
            }
        }
    }

    fun removeFromCart(productId: Int) {
        _cartItems.update { items -> items.filterNot { it.product.id == productId } }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}
