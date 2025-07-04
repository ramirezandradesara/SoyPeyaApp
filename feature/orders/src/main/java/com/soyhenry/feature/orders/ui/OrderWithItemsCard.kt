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
import com.soyhenry.core.model.database.entities.OrderWithItems
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrderWithItemsCard(orderWithItems: OrderWithItems) {
    fun formatDate(timestamp: Long): String {
        val pattern = "EEEE, dd MMM yyyy HH:mm"
        val sdf = SimpleDateFormat(pattern, Locale.ENGLISH)
        return sdf.format(Date(timestamp))
    }

    val formattedDate = formatDate(orderWithItems.order.orderDate)

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
                    text = "Order #${orderWithItems.order.id}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "$${"%.2f".format(orderWithItems.order.totalAmount)}",
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${orderWithItems.order.totalItems} products",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
