package com.soyhenry.library.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme

@Composable
fun AuthContainer(
    title: String,
    submitButtonText: String,
    onSubmitClick: () -> Unit,
    isSubmitEnabled: Boolean = true,
    bottomText: String,
    bottomActionText: String,
    onBottomActionClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(16.dp))

        content()

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onSubmitClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            enabled = isSubmitEnabled
        ) {
            Text(submitButtonText)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = bottomText)

            Spacer(modifier = Modifier.width(3.dp))

            Text(
                text = bottomActionText,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onBottomActionClick() }
            )
        }
    }
}
