package com.soyhenry.feature.register.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soyhenry.core.approutes.AppRoutes
import com.soyhenry.feature.register.viewmodel.RegisterViewModel
import com.soyhenry.library.ui.components.PasswordTextField

@Composable
fun RegisterView(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val email by viewModel.email.collectAsState()
    val name by viewModel.name.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()
    val registerSuccess by viewModel.registerSuccess.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val nameError by viewModel.nameError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val confirmPasswordError by viewModel.confirmPasswordError.collectAsState()

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearToastMessage()
        }
    }

    LaunchedEffect(registerSuccess) {
        if (registerSuccess) {
            navController.navigate(AppRoutes.Products.route) {
                popUpTo(AppRoutes.Register.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sign up",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null,
            supportingText = {
                if (emailError != null) {
                    Text(text = emailError ?: "")
                }
            }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = viewModel::onNameChange,
            label = { Text("Full name") },
            modifier = Modifier.fillMaxWidth(),
            isError = nameError != null,
            supportingText = {
                if (nameError != null) {
                    Text(text = nameError ?: "")
                }
            }
        )

        Spacer(Modifier.height(8.dp))

        PasswordTextField(
            value = password,
            onValueChange = viewModel::onPasswordChange,
            label = "Password",
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError != null,
            supportingText = {
                if (passwordError != null) {
                    Text(text = passwordError ?: "")
                }
            }
        )

        Spacer(Modifier.height(8.dp))

        PasswordTextField(
            value = confirmPassword,
            onValueChange = viewModel::onConfirmPasswordChange,
            label = "Confirm password",
            modifier = Modifier.fillMaxWidth(),
            isError = confirmPasswordError != null,
            supportingText = {
                if (confirmPasswordError != null) {
                    Text(text = confirmPasswordError ?: "")
                }
            }
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { viewModel.onRegisterClick(context) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            enabled = emailError == null &&
                    nameError == null &&
                    passwordError == null &&
                    confirmPasswordError == null
        ) {
            Text("Register")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Already have an account?")

            Spacer(modifier = Modifier.width(3.dp))

            Text(
                text = "Log in",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    navController.navigate(AppRoutes.LogIn.route) {
                        popUpTo(AppRoutes.Register.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterViewPreview() {
    val navController = rememberNavController()
    val viewModel: RegisterViewModel = hiltViewModel()

    RegisterView(navController, viewModel)
}