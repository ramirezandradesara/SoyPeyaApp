package com.soyhenry.feature.register.ui

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soyhenry.core.approutes.AppRoutes
import com.soyhenry.feature.register.viewmodel.RegisterViewModel
import com.soyhenry.library.ui.components.container.AuthContainer
import com.soyhenry.library.ui.components.textField.PasswordTextField
import androidx.compose.foundation.layout.*
import com.soyhenry.library.ui.components.textField.EmailTextField
import com.soyhenry.library.ui.components.textField.SimpleTextField

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

    val isFormValid = listOf(
        emailError,
        nameError,
        passwordError,
        confirmPasswordError
    ).all { it == null }

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

    AuthContainer(
        title = "Sign up, it's free!",
        submitButtonText = "Register",
        isSubmitEnabled = isFormValid,
        onSubmitClick = { viewModel.onRegisterClick() },
        bottomText = "Already have an account?",
        bottomActionText = "Log in",
        onBottomActionClick = {
            navController.navigate(AppRoutes.LogIn.route) {
                popUpTo(AppRoutes.Register.route) { inclusive = true }
            }
        }
    ) {
        EmailTextField(
            value = email,
            onValueChange = viewModel::onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            errorMessage = emailError,
        )

        Spacer(Modifier.height(8.dp))

        SimpleTextField(
            value = name,
            onValueChange = viewModel::onNameChange,
            label = "Full name",
            errorMessage = nameError,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        PasswordTextField(
            value = password,
            onValueChange = viewModel::onPasswordChange,
            label = "Password",
            modifier = Modifier.fillMaxWidth(),
            errorMessage = passwordError,
        )

        Spacer(Modifier.height(8.dp))

        PasswordTextField(
            value = confirmPassword,
            onValueChange = viewModel::onConfirmPasswordChange,
            label = "Confirm password",
            modifier = Modifier.fillMaxWidth(),
            errorMessage = confirmPasswordError,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterViewPreview() {
    val navController = rememberNavController()
    val viewModel: RegisterViewModel = hiltViewModel()

    RegisterView(navController, viewModel)
}
