package com.soyhenry.feature.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue

@Composable
fun LoginView(loginViewModel: LoginViewModel) {
    val context = LocalContext.current

    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val loginSuccess by loginViewModel.loginSuccess.collectAsState()

    val isFormValid = email.isNotBlank() && password.isNotBlank()

    LaunchedEffect(loginSuccess) {
        loginSuccess?.let {
            val message = if (it) "Welcome 🎉" else "Wrong user or password, please try again ❌"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            loginViewModel.resetLoginState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { loginViewModel.onEmailChange(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { loginViewModel.onPasswordChange(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { loginViewModel.onLoginClick() },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid
        ) {
            Text("Log in")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    LoginView(loginViewModel = LoginViewModel())
}