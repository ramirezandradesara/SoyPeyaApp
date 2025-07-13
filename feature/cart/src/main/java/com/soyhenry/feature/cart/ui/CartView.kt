package com.soyhenry.feature.cart.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.soyhenry.feature.cart.viewmodel.CartViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.soyhenry.core.approutes.AppRoutes
import com.soyhenry.core.state.UiState
import com.soyhenry.feature.orders.viewmodel.OrdersViewModel
import com.soyhenry.library.ui.components.EmptyState
import com.soyhenry.library.ui.components.container.ViewContainer
import androidx.compose.ui.res.stringResource
import com.soyhenry.feature.cart.R

@Composable
fun CartView(
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel(),
    ordersViewModel: OrdersViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by cartViewModel.uiState.collectAsState()

    fun navigateToProducts() {
        navController.navigate(AppRoutes.Products.route)
    }

    LaunchedEffect(Unit) {
        cartViewModel.refreshCartItems()
    }

    ViewContainer(title = stringResource(R.string.cart_title)) {
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
                        title = stringResource(R.string.cart_empty_title),
                        subtitle = stringResource(R.string.cart_empty_subtitle),
                        icon = Icons.Default.ShoppingCart,
                        buttonText = stringResource(R.string.browse_products),
                        onClick = { navigateToProducts() },
                    )
                } else {
                    LazyColumn {
                        items(cartItems) { item ->
                            CartItemCard(
                                item = item,
                                onIncrease = {
                                    cartViewModel.updateQuantity(
                                        item.product.id, item.cartItem.quantity + 1
                                    )
                                },
                                onDecrease = {
                                    if (item.cartItem.quantity > 1) {
                                        cartViewModel.updateQuantity(
                                            item.product.id, item.cartItem.quantity - 1
                                        )
                                    } else {
                                        cartViewModel.removeFromCart(item.cartItem)
                                    }
                                })
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    CartSummaryRow(
                        label = stringResource(R.string.total_items),
                        value = totalItems.toString()
                    )
                    CartSummaryRow(
                        label = stringResource(R.string.total_amount),
                        value = "$${"%.2f".format(totalAmount)}",
                    )
                    Button(
                        onClick = {
                            ordersViewModel.createOrder(
                                cartItems = state.data,
                                onSuccess = {
                                    cartViewModel.removeAllFromCart()
                                    navController.navigate(AppRoutes.Orders.route) },
                                onError = { message ->
                                    Toast.makeText(context, "Error: $message", Toast.LENGTH_LONG).show()
                                }
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.create_order))
                    }
                }
            }

            is UiState.Error -> {
                Text(text = stringResource(R.string.error_message, state.message))
            }
        }
    }
}
