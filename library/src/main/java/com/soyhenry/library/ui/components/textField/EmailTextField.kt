package com.soyhenry.library.ui.components.textField

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Email",
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    imeAction: ImeAction = ImeAction.Next,
) {
    BaseTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        modifier = modifier,
        isError = errorMessage != null,
        errorMessage = errorMessage,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        )
    )
}