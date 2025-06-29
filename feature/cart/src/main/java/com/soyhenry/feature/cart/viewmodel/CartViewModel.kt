package com.soyhenry.feature.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyhenry.core.model.database.entities.CartItemEntity
import com.soyhenry.core.model.database.entities.CartItemWithProductEntity
import com.soyhenry.core.model.database.entities.ProductEntity
import com.soyhenry.core.repository.CartItemRepository
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

    private val _cartItems = MutableStateFlow<List<CartItemWithProductEntity>>(emptyList())
    val cartItems: StateFlow<List<CartItemWithProductEntity>> = _cartItems.asStateFlow()

   /* fun addToCart(product: Product) {
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


    fun removeFromCart(productId: Int) {
        _cartItems.update { items -> items.filterNot { it.product.id == productId } }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    } */

    private suspend fun initializeCartItems(){

    }
    init {
        refreshCartItems()
    }

    private fun refreshCartItems() {
        viewModelScope.launch {
            cartItemRepository.getAllCartItemsWithProducts().collect { items ->
                _cartItems.value = items
            }
        }
    }

    /*fun updateQuantity(productId: Int, newQuantity: Int) {
        _cartItems.update { items ->
            items.map {
                if (it.product.id == productId) it.copy(quantity = newQuantity.coerceAtLeast(1))
                else it
            }
        }
    } */

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
            val existingCartItem = cartItemRepository.getCartItemByProductId(product.id)

            if (existingCartItem != null) {
                val updatedCartItem = existingCartItem.copy(quantity = existingCartItem.quantity + 1)
                cartItemRepository.updateCartItem(updatedCartItem)
            } else {
                cartItemRepository.insertCartItem(
                    CartItemEntity(productId = product.id, quantity = 1)
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
            //delay(500L)
            cartItemRepository.deleteCartItems()
            refreshCartItems()
        }
    }
}
