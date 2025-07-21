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
import com.soyhenry.core.constants.AppRoutes
import com.soyhenry.feature.register.viewmodel.RegisterViewModel
import com.soyhenry.library.ui.components.container.AuthContainer
import com.soyhenry.library.ui.components.textField.PasswordTextField
import androidx.compose.foundation.layout.*
import com.soyhenry.library.ui.components.textField.EmailTextField
import com.soyhenry.library.ui.components.textField.SimpleTextField
import androidx.compose.ui.res.stringResource
import com.soyhenry.feature.register.R

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
    val isLoading by viewModel.isLoading.collectAsState()
    val registerSuccess by viewModel.registerSuccess.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val nameError by viewModel.nameError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val confirmPasswordError by viewModel.confirmPasswordError.collectAsState()

    val isSubmitEnabled = listOf(
        emailError,
        nameError,
        passwordError,
        confirmPasswordError
    ).all { it == null } && !isLoading

    toastMessage?.let { message ->
        LaunchedEffect(message) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
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
        title = stringResource(id = R.string.sign_up_free),
        submitButtonText = stringResource(id = R.string.register_button),
        isSubmitEnabled = isSubmitEnabled,
        isLoading = isLoading,
        onSubmitClick = { viewModel.onRegisterClick() },
        bottomText = stringResource(id = R.string.already_have_account),
        bottomActionText = stringResource(id = R.string.log_in),
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
            label = stringResource(id = R.string.full_name_label),
            errorMessage = nameError,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        PasswordTextField(
            value = password,
            onValueChange = viewModel::onPasswordChange,
            label = stringResource(id = R.string.password_label),
            modifier = Modifier.fillMaxWidth(),
            errorMessage = passwordError,
        )

        Spacer(Modifier.height(8.dp))

        PasswordTextField(
            value = confirmPassword,
            onValueChange = viewModel::onConfirmPasswordChange,
            label = stringResource(id = R.string.confirm_password_label),
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
