package com.soyhenryfeature.products.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.MaterialTheme

@Composable
fun ProductListHeader(
    filterText: String,
    onFilterTextChange: (String) -> Unit
) {
    Text(
        text = "Products 🍔",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(vertical = 16.dp)
    )

    OutlinedTextField(
        value = filterText,
        onValueChange = onFilterTextChange,
        label = { Text("Filter products") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        singleLine = true
    )
}
