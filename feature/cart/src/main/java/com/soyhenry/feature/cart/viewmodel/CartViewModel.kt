package com.soyhenry.feature.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyhenry.core.model.database.entities.CartItemEntity
import com.soyhenry.core.model.database.entities.CartItemWithProductEntity
import com.soyhenry.core.model.database.entities.ProductEntity
import com.soyhenry.core.repository.CartItemRepository
import com.soyhenry.core.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartItemRepository: CartItemRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<CartItemWithProductEntity>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<CartItemWithProductEntity>>> = _uiState.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItemWithProductEntity>>(emptyList())
    val cartItems: StateFlow<List<CartItemWithProductEntity>> = _cartItems.asStateFlow()

    init {
        refreshCartItems()
    }

    private fun refreshCartItems() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                cartItemRepository.getAllCartItemsWithProducts().collect { items ->
                    _uiState.value = UiState.Success(items)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error loading cart: ${e.message}")
            }
        }
    }

    fun updateQuantity(productId: String, newQuantity: Int) {
        viewModelScope.launch {
            cartItemRepository.getCartItemByProductId(productId)?.let { existing ->
                val updated = existing.copy(quantity = newQuantity.coerceAtLeast(1))
                cartItemRepository.updateCartItem(updated)
            }
        }
    }

    fun addToCart(product: ProductEntity) {
        viewModelScope.launch {
            val existingCartItem = cartItemRepository.getCartItemByProductId(product._id)

            if (existingCartItem != null) {
                val updatedCartItem = existingCartItem.copy(quantity = existingCartItem.quantity + 1)
                cartItemRepository.updateCartItem(updatedCartItem)
            } else {
                cartItemRepository.insertCartItem(
                    CartItemEntity(productId = product._id, quantity = 1)
                )
            }
            refreshCartItems()
        }
    }

    fun removeFromCart(cartItem: CartItemEntity) {
        viewModelScope.launch {
            cartItemRepository.getCartItemByProductId(cartItem.productId)?.let {
                cartItemRepository.deleteCartItem(it.id)
            }
            refreshCartItems()
        }
    }

    fun removeAllFromCart() {
        viewModelScope.launch {
            cartItemRepository.deleteCartItems()
            refreshCartItems()
        }
    }
}
