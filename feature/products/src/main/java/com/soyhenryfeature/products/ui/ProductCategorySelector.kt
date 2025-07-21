package com.soyhenryfeature.products.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.soyhenry.core.model.state.UiState
import com.soyhenryfeature.products.R
import com.soyhenryfeature.products.model.UiCategory
import androidx.compose.foundation.lazy.items

@Composable
fun ProductCategorySelector(
    categoriesState: UiState<List<UiCategory>>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        when (categoriesState) {
            is UiState.Loading -> {
                item { CircularProgressIndicator() }
            }
            is UiState.Success -> {
                items(categoriesState.data) { category ->
                    ProductCategoryCard(
                        category = category,
                        isSelected = selectedCategory == category.value,
                        onClick = { onCategorySelected(category.value) }
                    )
                }
            }
            is UiState.Error -> {
                item {
                    Text(
                        text = stringResource(R.string.category_load_error),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
