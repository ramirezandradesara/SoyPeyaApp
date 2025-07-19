package com.soyhenryfeature.products.ui

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soyhenryfeature.products.R
import androidx.compose.ui.res.stringResource

@Composable
fun FilterBottomSheetContent(
    selectedCategory: String?,
    selectedPrice: Float,
    onApplyFilters: (String?, Float) -> Unit
) {
    var localCategory by remember { mutableStateOf(selectedCategory) }
    var localPrice by remember { mutableFloatStateOf(selectedPrice) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.filters_title),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(stringResource(R.string.filter_category_subtitle))

        Spacer(modifier = Modifier.height(8.dp))

        val categories = listOf<String?>(null, "Taco", "Pizza", "Burger")
        categories.forEach { category ->
            val categoryLabel = category ?: "Todos"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { localCategory = category }
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = localCategory == category,
                    onClick = { localCategory = category }
                )
                Text(categoryLabel)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Filtrar por precio: hasta $${localPrice.toInt()}")
        Slider(
            value = localPrice,
            onValueChange = { localPrice = it },
            valueRange = 0f..200f,
            steps = 0
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onApplyFilters(localCategory, localPrice) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.apply_filter_button))
        }
    }
}
