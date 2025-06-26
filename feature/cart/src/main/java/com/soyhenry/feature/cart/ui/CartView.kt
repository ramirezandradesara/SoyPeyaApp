package com.soyhenry.feature.cart.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soyhenry.feature.cart.data.model.CartItem
import com.soyhenry.feature.cart.viewmodel.CartViewModel

@Composable
fun CartView(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val image =
        "https://media.istockphoto.com/id/1442417585/es/foto/persona-recibiendo-un-pedazo-de-pizza-de-pepperoni-con-queso.jpg?s=612x612&w=0&k=20&c=Uk4fj96OIDxE4v2S5sRRXRY_gZ899_TE6jGD-T-TysI="

    val cartItems = listOf(
        CartItem(1, "Melting Cheese Pizza", "Pizza Italiano", 11.88, image),
        CartItem(2, "Veggie Delight", "Pizza Vegana", 10.50, image),
        CartItem(3, "Pepperoni Supreme", "Pizza Americana", 12.25, image)
    )

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

        cartItems.forEach { item ->
            CartItemCard(item)
            Spacer(modifier = Modifier.height(12.dp))
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
