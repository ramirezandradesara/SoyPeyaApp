package com.soyhenry.library.ui.components.textField

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
) {
    var passwordVisible by remember { mutableStateOf(false) }

    BaseTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        isError = errorMessage != null,
        errorMessage = errorMessage,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
            val description = if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = icon, contentDescription = description)
            }
        }
    )
}
@Preview(showBackground = true)
@Composable
fun PasswordTextFieldPreview() {
    var password by remember { mutableStateOf("admin123") }

    PasswordTextField(
        value = password,
        onValueChange = { password = it },
        label = "Password",
        errorMessage = "Password is required",
    )
}