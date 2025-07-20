package com.soyhenry.feature.orders.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soyhenry.feature.orders.viewmodel.OrdersViewModel
import com.soyhenry.library.ui.components.container.ViewContainer
import com.soyhenry.core.state.UiState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.soyhenry.core.approutes.AppRoutes
import com.soyhenry.feature.orders.R
import com.soyhenry.library.ui.components.LoadingScreen
import com.soyhenry.library.ui.components.state.EmptyState
import com.soyhenry.library.ui.components.state.ErrorState

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
            is UiState.Loading -> LoadingScreen()

            is UiState.Success -> {
                val orders = state.data
                if (orders.isEmpty()) {
                    EmptyState(
                        title = stringResource(R.string.no_orders_title),
                        subtitle = stringResource(R.string.no_orders_subtitle),
                        icon = Icons.Default.Receipt,
                        buttonText = stringResource(R.string.browse_products),
                        onClick = { navController.navigate(AppRoutes.Products.route) },
                    )
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

            is UiState.Error -> ErrorState(message = state.message)
        }
    }
}
