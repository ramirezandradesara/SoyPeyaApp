package com.soyhenry.feature.orders.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soyhenry.core.domain.Order
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.ui.res.stringResource
import com.soyhenry.feature.orders.R

@Composable
fun OrderCard(
    orderWithItems: Order
) {
    fun formatDate(timestamp: Long): String {
        val pattern = "EEEE, dd MMM yyyy HH:mm"
        val sdf = SimpleDateFormat(pattern, Locale("es"))
        return sdf.format(Date(timestamp))
    }

    val formattedDate = formatDate(orderWithItems.date)

    val productsText = if (orderWithItems.totalItems == 1) {
        stringResource(id = R.string.product_singular, orderWithItems.totalItems)
    } else {
        stringResource(id = R.string.product_plural, orderWithItems.totalItems)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                formattedDate,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(
                        id = R.string.order_number,
                        orderWithItems.id.takeLast(8)
                    ),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "$${"%.2f".format(orderWithItems.totalAmount)}",
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = productsText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
