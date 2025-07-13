package com.soyhenry.feature.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyhenry.core.domain.CartItem
import com.soyhenry.core.domain.Product
import com.soyhenry.core.entities.CartItemEntity
import com.soyhenry.core.state.UiState
import com.soyhenry.feature.cart.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CartViewModel @Inject constructor(
    private val insertCartItemUseCase: InsertCartItemUseCase,
    private val getAllCartItemsWithProductsUseCase: GetAllCartItemsWithProductsUseCase,
    private val getCartItemByProductIdUseCase: GetCartItemByProductIdUseCase,
    private val updateCartItemUseCase: UpdateCartItemUseCase,
    private val deleteCartItemByIdUseCase: DeleteCartItemByIdUseCase,
    private val deleteCartItemsUseCase: DeleteCartItemsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<CartItem>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<CartItem>>> = _uiState.asStateFlow()

    fun refreshCartItems() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                getAllCartItemsWithProductsUseCase().collect { items ->
                    _uiState.value = UiState.Success(items)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error loading cart: ${e.message}")
            }
        }
    }

    fun updateQuantity(productId: String, newQuantity: Int) {
        viewModelScope.launch {
            getCartItemByProductIdUseCase(productId)?.let { existing ->
                val updated = existing.copy(quantity = newQuantity.coerceAtLeast(1))
                insertCartItemUseCase(updated)
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val existingCartItem = getCartItemByProductIdUseCase(product.id)

            if (existingCartItem != null) {
                val updatedCartItem = existingCartItem.copy(quantity = existingCartItem.quantity + 1)
                updateCartItemUseCase(updatedCartItem)
            } else {
                insertCartItemUseCase(CartItemEntity(productId = product.id, quantity = 1))
            }
            refreshCartItems()
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            getCartItemByProductIdUseCase(cartItem.productId)?.let {
                deleteCartItemByIdUseCase(it.id)
            }
            refreshCartItems()
        }
    }

    fun removeAllFromCart() {
        viewModelScope.launch {
            deleteCartItemsUseCase()
            refreshCartItems()
        }
    }
}
