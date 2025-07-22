package com.soyhenry.library.ui.components.container

import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ViewContainer(
    title: String,
    modifier: Modifier = Modifier,
    icon: (@Composable (() -> Unit))? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Medium)
            )
            icon?.let {
                it()
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            content = content
        )
    }
}
