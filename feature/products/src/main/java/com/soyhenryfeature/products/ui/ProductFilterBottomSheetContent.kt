package com.soyhenryfeature.products.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.soyhenry.core.model.state.UiState
import com.soyhenryfeature.products.R
import com.soyhenryfeature.products.model.UiCategory

@Composable
fun ProductFilterBottomSheetContent(
    categoryState: UiState<List<UiCategory>>,
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

        Spacer(modifier = Modifier.height(8.dp))

        Text(stringResource(R.string.filter_by_category_subtitle))

        Spacer(modifier = Modifier.height(8.dp))

        ProductCategorySelector(
            categoriesState = categoryState,
            selectedCategory = localCategory,
            onCategorySelected = { localCategory = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = stringResource(R.string.filter_by_price_subtitle, localPrice.toInt()))
        Slider(
            value = localPrice,
            onValueChange = { localPrice = it },
            valueRange = 0f..200f
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
