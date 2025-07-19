package com.soyhenryfeature.products.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.res.stringResource
import com.soyhenryfeature.products.R
import androidx.compose.ui.Alignment

@Composable
fun FilterBar(
    filterText: String,
    onFilterTextChange: (String) -> Unit,
    onOpenFilters: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = filterText,
            onValueChange = onFilterTextChange,
            label = { Text(stringResource(R.string.filter_products_label)) },
            modifier = Modifier.weight(1f).padding(end = 8.dp),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon_content_desc)
                )
            }
        )

        IconButton(onClick = onOpenFilters) {
            Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = stringResource(R.string.filter_icon_content_desc)
            )
        }
    }
}
