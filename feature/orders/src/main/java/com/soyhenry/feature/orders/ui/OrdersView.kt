package com.soyhenry.feature.orders.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.soyhenry.feature.orders.viewmodel.OrdersViewModel
import com.soyhenry.library.ui.components.container.ViewContainer
import com.soyhenry.core.state.UiState
import androidx.compose.ui.res.stringResource
import com.soyhenry.feature.orders.R

@Composable
fun OrdersView(
    navController: NavController,
    ordersViewModel: OrdersViewModel = hiltViewModel()
) {
    val uiState by ordersViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        ordersViewModel.loadOrders(refreshData = true)
    }

    ViewContainer(title = stringResource(id = R.string.order_history)) {
        when (val state = uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                val orders = state.data
                if (orders.isEmpty()) {
                    Text(stringResource(id = R.string.no_orders_found))
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(orders) { orderWithItems ->
                            OrderCard(orderWithItems)
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }

            is UiState.Error -> {
                Text(stringResource(id = R.string.error_loading_orders, state.message))
            }
        }
    }
}
