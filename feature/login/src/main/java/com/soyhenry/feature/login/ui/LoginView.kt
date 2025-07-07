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
import com.soyhenry.core.approutes.AppRoutes
import com.soyhenry.core.constants.appinfo.AppInfo
import com.soyhenry.feature.login.viewmodel.LoginViewModel
import com.soyhenry.library.ui.components.AuthContainer
import com.soyhenry.library.ui.components.textField.EmailTextField
import com.soyhenry.library.ui.components.textField.PasswordTextField

@Composable
fun LoginView(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val loginSuccess by loginViewModel.loginSuccess.collectAsState()
    val toastMessage by loginViewModel.toastMessage.collectAsState()

    val isFormValid = email.isNotBlank() && password.isNotBlank()

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
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
        title = "Welcome back!",
        submitButtonText = "Log in",
        onSubmitClick = { loginViewModel.onLoginClick(context) },
        isSubmitEnabled = isFormValid,
        bottomText = "New to ${AppInfo.APP_NAME}?",
        bottomActionText = "Sign up",
        onBottomActionClick = {
            navController.navigate(AppRoutes.Register.route) {
                popUpTo(AppRoutes.LogIn.route) { inclusive = true }
            }
        }
    ) {
        EmailTextField(
            value = email,
            onValueChange = loginViewModel::onEmailChange,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(8.dp))

        PasswordTextField(
            value = password,
            onValueChange = loginViewModel::onPasswordChange,
            label = "Password",
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