package com.soyhenry.feature.cart.ui

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.soyhenry.feature.cart.viewmodel.CartViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.platform.LocalContext
import com.soyhenry.core.constants.AppRoutes
import com.soyhenry.core.model.state.UiState
import com.soyhenry.feature.orders.viewmodel.OrdersViewModel
import com.soyhenry.library.ui.components.state.EmptyState
import com.soyhenry.library.ui.components.container.ViewContainer
import androidx.compose.ui.res.stringResource
import com.soyhenry.feature.cart.R
import com.soyhenry.library.ui.components.LoadingScreen
import com.soyhenry.library.ui.components.state.ErrorState

@Composable
fun CartView(
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel(),
    ordersViewModel: OrdersViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by cartViewModel.uiState.collectAsState()
    val isLoading by ordersViewModel.isLoading.collectAsState()
    val toastMessage by cartViewModel.toastMessage.collectAsState()

    toastMessage?.let { message ->
        LaunchedEffect(message) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            cartViewModel.clearToastMessage()
        }
    }

    LaunchedEffect(Unit) {
        cartViewModel.refreshCartItems()
    }

    ViewContainer(title = stringResource(R.string.cart_title)) {
        when (val state = uiState) {
            is UiState.Loading -> LoadingScreen()

            is UiState.Success -> {
                val cartItems = state.data
                if (cartItems.isEmpty()) {
                    EmptyState(
                        title = stringResource(R.string.cart_empty_title),
                        subtitle = stringResource(R.string.cart_empty_subtitle),
                        icon = Icons.Default.ShoppingCart,
                        buttonText = stringResource(R.string.browse_products),
                        onClick = { navController.navigate(AppRoutes.Products.route) },
                    )
                } else {
                    CartListSection(
                        cartItems = cartItems,
                        isLoading = isLoading,
                        onIncrease = { item ->
                            cartViewModel.updateQuantity(item.product.id, item.quantity + 1)
                        },
                        onDecrease = { item ->
                            if (item.quantity > 1) {
                                cartViewModel.updateQuantity(item.product.id, item.quantity - 1)
                            } else {
                                cartViewModel.removeFromCart(item)
                            }
                        },
                        onCreateOrder = {
                            ordersViewModel.createOrder(
                                cartItems = cartItems,
                                onSuccess = {
                                    cartViewModel.removeAllFromCart()
                                    navController.navigate(AppRoutes.Orders.route)
                                },
                                onError = { message ->
                                    Toast.makeText(context, context.getString(R.string.error_message, message), Toast.LENGTH_LONG).show()
                                }
                            )
                        }
                    )
                }
            }

            is UiState.Error -> ErrorState(message = state.message)
        }
    }
}
