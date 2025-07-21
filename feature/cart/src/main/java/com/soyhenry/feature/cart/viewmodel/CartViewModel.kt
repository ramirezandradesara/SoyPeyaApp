package com.soyhenry.feature.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyhenry.core.model.domain.CartItem
import com.soyhenry.core.model.domain.Product
import com.soyhenry.core.model.entities.CartItemEntity
import com.soyhenry.core.model.state.UiState
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

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

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
            try {
                getCartItemByProductIdUseCase(productId)?.let { existing ->
                    val updated = existing.copy(quantity = newQuantity.coerceAtLeast(1))
                    insertCartItemUseCase(updated)
                }
            } catch (e: Exception) {
                _toastMessage.value = "Error al actualizar cantidad: ${e.message}"
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            try {
                val existingCartItem = getCartItemByProductIdUseCase(product.id)

                if (existingCartItem != null) {
                    val updatedCartItem = existingCartItem.copy(quantity = existingCartItem.quantity + 1)
                    updateCartItemUseCase(updatedCartItem)
                } else {
                    insertCartItemUseCase(CartItemEntity(productId = product.id, quantity = 1))
                }
                refreshCartItems()
            } catch (e: Exception) {
                _toastMessage.value = "Error al agregar al carrito: ${e.message}"
            }
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            try {
                getCartItemByProductIdUseCase(cartItem.productId)?.let {
                    deleteCartItemByIdUseCase(it.id)
                }
                refreshCartItems()
            } catch (e: Exception) {
                _toastMessage.value = "Error al eliminar del carrito: ${e.message}"
            }
        }
    }

    fun removeAllFromCart() {
        viewModelScope.launch {
            try {
                deleteCartItemsUseCase()
                refreshCartItems()
            } catch (e: Exception) {
                _toastMessage.value = "Error al eliminar todos los productos: ${e.message}"
            }
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }
}
