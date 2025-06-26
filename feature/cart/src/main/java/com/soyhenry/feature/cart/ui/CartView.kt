package com.soyhenry.feature.cart.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soyhenry.feature.cart.viewmodel.CartViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CartView(
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val cartItems by cartViewModel.cartItems.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Cart 🛒",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(cartItems) { item ->
                CartItemCard(
                    item = item,
                    onIncrease = {
                        cartViewModel.updateQuantity(item.product.id, item.quantity + 1)
                    },
                    onDecrease = {
                        if (item.quantity > 1) {
                            cartViewModel.updateQuantity(item.product.id, item.quantity - 1)
                        } else {
                            cartViewModel.removeFromCart(item.product.id)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartViewPreview() {
    val navController = rememberNavController()
    val viewModel: CartViewModel = hiltViewModel()

    CartView(navController, viewModel)
}
