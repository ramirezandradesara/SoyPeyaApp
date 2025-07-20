package com.soyhenry.feature.cart.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.soyhenry.core.domain.CartItem
import com.soyhenry.feature.cart.R
import com.soyhenry.library.ui.components.button.LoadingButton

@Composable
fun CartListSection(
    cartItems: List<CartItem>,
    isLoading: Boolean,
    onIncrease: (CartItem) -> Unit,
    onDecrease: (CartItem) -> Unit,
    onCreateOrder: () -> Unit,
) {
    val totalItems = cartItems.sumOf { it.quantity }
    val totalAmount = cartItems.sumOf { it.quantity * it.product.price }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(cartItems) { item ->
            CartItemCard(
                item = item,
                onIncrease = { onIncrease(item) },
                onDecrease = { onDecrease(item) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            CartSummaryRow(
                label = stringResource(R.string.total_items),
                value = totalItems.toString()
            )
            CartSummaryRow(
                label = stringResource(R.string.total_amount),
                value = "$${"%.2f".format(totalAmount)}"
            )
        }

        item {
            LoadingButton(
                text = stringResource(R.string.create_order),
                isLoading = isLoading,
                onClick = onCreateOrder,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
            )
        }
    }
}
