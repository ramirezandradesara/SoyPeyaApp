package com.soyhenry.library.ui.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyhenry.library.ui.theme.SoyPeyaAppTheme

@Composable
fun LoadingButton(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loadingText: String = "",
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
                val displayedLoadingText = if (loadingText.isBlank()) text else loadingText
                Text(
                    text = displayedLoadingText,
                    modifier = Modifier.padding(start = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(text)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingButtonPreview() {
    SoyPeyaAppTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Botón normal:", style = MaterialTheme.typography.labelLarge)
            LoadingButton(
                text = "Enviar",
                onClick = { },
                isLoading = false,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Botón cargando con 'loadingText':", style = MaterialTheme.typography.labelLarge)
            LoadingButton(
                text = "Enviar",
                onClick = { },
                isLoading = true,
                loadingText = "Procesando..."
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Botón cargando sin 'loadingText' (usa el texto original):", style = MaterialTheme.typography.labelLarge)
            LoadingButton(
                text = "Enviar",
                onClick = { },
                isLoading = true,
                loadingText = ""
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Botón deshabilitado (no cargando):", style = MaterialTheme.typography.labelLarge)
            LoadingButton(
                text = "Deshabilitado",
                onClick = { },
                isLoading = false,
                enabled = false
            )
        }
    }
}