package com.soyhenry.feature.cart.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.soyhenry.feature.cart.viewmodel.CartViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soyhenry.core.approutes.AppRoutes
import com.soyhenry.core.state.UiState
import com.soyhenry.library.ui.components.EmptyState

@Composable
fun CartView(
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val uiState by cartViewModel.uiState.collectAsState()

    fun navigateToProducts() {
        navController.navigate(AppRoutes.Products.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Cart",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (val state = uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                val cartItems = state.data
                val totalItems = cartItems.sumOf { it.cartItem.quantity }
                val totalAmount = cartItems.sumOf { it.cartItem.quantity * it.product.price }

                if (cartItems.isEmpty()) {
                    EmptyState(
                        title = "Your cart is empty",
                        subtitle = "Start selecting products to add to your cart.",
                        icon = Icons.Default.ShoppingCart,
                        buttonText = "Browse products",
                        onClick = { navigateToProducts() },
                    )
                } else {
                    LazyColumn {
                        items(cartItems) { item ->
                            CartItemCard(
                                item = item,
                                onIncrease = {
                                    cartViewModel.updateQuantity(
                                        item.product.id,
                                        item.cartItem.quantity + 1
                                    )
                                },
                                onDecrease = {
                                    if (item.cartItem.quantity > 1) {
                                        cartViewModel.updateQuantity(
                                            item.product.id,
                                            item.cartItem.quantity - 1
                                        )
                                    } else {
                                        cartViewModel.removeFromCart(item.cartItem)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Total items: $totalItems",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        text = "Total amount: $${"%.2f".format(totalAmount)}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            is UiState.Error -> {
                Text("Error: ${state.message}")
            }
        }
    }
}
