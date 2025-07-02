package com.soyhenry.feature.orders.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.soyhenry.core.model.database.entities.OrderEntity
import com.soyhenry.core.model.database.entities.OrderItemEntity
import com.soyhenry.core.model.database.entities.OrderWithItems
import com.soyhenry.feature.orders.viewmodel.OrdersViewModel
import com.soyhenry.library.ui.components.ViewContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersView(
    navController: NavController,
    ordersViewModel: OrdersViewModel = hiltViewModel()
) {
    val orders = listOf(
        OrderWithItems(
            order = OrderEntity(
                id = 1,
                orderDate = System.currentTimeMillis() - 86_400_000L,
                totalAmount = 350.0,
                totalItems = 2
            ),
            items = listOf(
                OrderItemEntity(
                    orderItemId = 1,
                    orderId = 1,
                    productId = "A1",
                    quantity = 1,
                    price = 150.0
                ),
                OrderItemEntity(
                    orderItemId = 2,
                    orderId = 1,
                    productId = "B2",
                    quantity = 2,
                    price = 100.0
                )
            )
        ),
        OrderWithItems(
            order = OrderEntity(
                id = 2,
                orderDate = System.currentTimeMillis() - 3 * 86_400_000L,
                totalAmount = 99.99,
                totalItems = 1
            ),
            items = listOf(
                OrderItemEntity(
                    orderItemId = 3,
                    orderId = 2,
                    productId = "C3",
                    quantity = 1,
                    price = 99.99
                )
            )
        )
    )

    ViewContainer(title = "Order history") {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(orders) { orderWithItems ->
                OrderWithItemsCard(orderWithItems)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrdersViewPreview() {
    val sampleOrders = listOf(
        OrderWithItems(
            order = OrderEntity(
                id = 1,
                orderDate = System.currentTimeMillis(),
                totalAmount = 250.0,
                totalItems = 3
            ),
            items = listOf(
                OrderItemEntity(
                    orderItemId = 1,
                    orderId = 1,
                    productId = "P001",
                    quantity = 1,
                    price = 100.0
                ),
                OrderItemEntity(
                    orderItemId = 2,
                    orderId = 1,
                    productId = "P002",
                    quantity = 2,
                    price = 75.0
                )
            )
        )
    )

    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            sampleOrders.forEach { OrderWithItemsCard(it) }
        }
    }
}
