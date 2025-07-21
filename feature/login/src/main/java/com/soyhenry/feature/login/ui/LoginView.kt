package com.soyhenry.feature.login.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.soyhenry.core.constants.AppRoutes
import com.soyhenry.core.constants.AppInfo
import com.soyhenry.feature.login.viewmodel.LoginViewModel
import com.soyhenry.library.ui.components.container.AuthContainer
import com.soyhenry.library.ui.components.textField.EmailTextField
import com.soyhenry.library.ui.components.textField.PasswordTextField
import androidx.compose.ui.res.stringResource
import com.soyhenry.feature.login.R

@Composable
fun LoginView(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val emailError by loginViewModel.emailError.collectAsState()
    val passwordError by loginViewModel.passwordError.collectAsState()
    val isLoading by loginViewModel.isLoading.collectAsState()
    val loginSuccess by loginViewModel.loginSuccess.collectAsState()
    val toastMessage by loginViewModel.toastMessage.collectAsState()

    val isSubmitEnabled = email.isNotBlank() && password.isNotBlank() && !isLoading

    toastMessage?.let { message ->
        LaunchedEffect(message) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            loginViewModel.clearToastMessage()
        }
    }

    LaunchedEffect(loginSuccess) {
        if (loginSuccess == true) {
            navController.navigate(AppRoutes.Products.route) {
                popUpTo(AppRoutes.LogIn.route) { inclusive = true }
            }
        }
    }

    AuthContainer(
        title = stringResource(id = R.string.welcome_back),
        submitButtonText = stringResource(id = R.string.log_in),
        onSubmitClick = { loginViewModel.onLoginClick() },
        isSubmitEnabled = isSubmitEnabled,
        isLoading = isLoading,
        bottomText = stringResource(id = R.string.new_to_app, AppInfo.APP_NAME),
        bottomActionText = stringResource(id = R.string.sign_up),
        onBottomActionClick = {
            navController.navigate(AppRoutes.Register.route) {
                popUpTo(AppRoutes.LogIn.route) { inclusive = true }
            }
        }
    ) {
        EmailTextField(
            value = email,
            onValueChange = loginViewModel::onEmailChange,
            errorMessage = emailError,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(8.dp))

        PasswordTextField(
            value = password,
            onValueChange = loginViewModel::onPasswordChange,
            errorMessage = passwordError,
            label = stringResource(id = R.string.password_label),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val navController = rememberNavController()
    
    LoginView(navController, loginViewModel)
}