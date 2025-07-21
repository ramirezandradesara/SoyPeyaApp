package com.soyhenry.feature.orders.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyhenry.core.model.domain.CartItem
import com.soyhenry.core.model.domain.Order
import com.soyhenry.core.model.state.UiState
import com.soyhenry.data.remote.dto.CartItemDto
import com.soyhenry.data.remote.dto.OrderDto
import com.soyhenry.feature.orders.domain.usecase.CreateOrderUseCase
import com.soyhenry.feature.orders.domain.usecase.GetOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val createOrderUseCase: CreateOrderUseCase,
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Order>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Order>>> = _uiState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadOrders (refreshData: Boolean = false) {
        viewModelScope.launch {
            try {
                val orders = getOrdersUseCase(refreshData)
                _uiState.value = UiState.Success(orders)
            } catch(e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error al cargar órdenes")
            }
        }
    }

    fun createOrder(
        cartItems: List<CartItem>,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            val orderRequest = OrderDto(
                orderId = UUID.randomUUID().toString(),
                productIds = cartItems.map {
                    CartItemDto(
                        productId = it.product.id,
                        name = it.product.name,
                        description = "",
                        imageUrl = it.product.imgURL,
                        price = it.product.price,
                        hasDrink = false,
                        quantity = it.quantity
                    )
                },
                total = cartItems.sumOf { it.quantity * it.product.price },
                timestamp = System.currentTimeMillis()
            )

            try {
                createOrderUseCase(orderRequest)
                onSuccess()
            } catch (e: HttpException) {
                val message = when (e.code()) {
                    400 -> "Error en la solicitud: por favor, verifique sus datos"
                    500 -> "Error del servidor. Por favor, inténtalo de nuevo más tarde"
                    else -> "Error: ${e.code()}"
                }
                onError(message)
            } catch (e: IOException) {
                onError("Error de red. Verifica tu conexión")
            } catch (e: Exception) {
                onError("Error inesperado: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
