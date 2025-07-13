package com.soyhenry.feature.orders.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyhenry.core.domain.Order
import com.soyhenry.core.entities.CartItemWithProductEntity
import com.soyhenry.core.state.UiState
import com.soyhenry.data.remote.dto.CartItemDto
import com.soyhenry.data.remote.dto.OrderDto
import com.soyhenry.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Order>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Order>>> = _uiState

    fun loadOrders () {
        viewModelScope.launch {
            try {
                val orders = orderRepository.getOrders()
                _uiState.value = UiState.Success(orders)
            } catch(e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error loading orders")
            }
        }
    }

    fun createOrder(
        cartItems: List<CartItemWithProductEntity>,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val orderRequest = OrderDto(
                orderId = UUID.randomUUID().toString(),
                productIds = cartItems.map {
                    CartItemDto(
                        name = it.product.productName,
                        //description = it.product.description ?: "Sin descripción",
                        description = "Sin descripción",
                        imageUrl = it.product.imageURL,
                        price = it.product.price,
                        //hasDrink = it.product.hasDrink ?: false,
                        hasDrink = false,
                        quantity = it.cartItem.quantity
                    )
                },
                total = cartItems.sumOf { it.cartItem.quantity * it.product.price },
                timestamp = System.currentTimeMillis()
            )

            try {
                orderRepository.createOrder(orderRequest)
                onSuccess()
            } catch (e: Exception) {
                Log.e("OrderViewModel", "Error creating order", e)
                onError(e.message ?: "Error desconocido")
            }
        }
    }
}
