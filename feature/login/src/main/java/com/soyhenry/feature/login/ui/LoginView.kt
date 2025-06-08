package com.soyhenry.feature.login.ui

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.soyhenry.core.approutes.AppRoutes
import com.soyhenry.core.constants.appinfo.AppInfo
import com.soyhenry.feature.login.viewmodel.LoginViewModel
import com.soyhenry.library.ui.components.PasswordTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    loginViewModel: LoginViewModel,
    navController: NavController
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
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
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
            onValueChange = loginViewModel::onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        PasswordTextField(
            value = password,
            onValueChange = loginViewModel::onPasswordChange,
            label = "Password",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { loginViewModel.onLoginClick(context) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            enabled = isFormValid
        ) {
            Text("Log in")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "New to ${AppInfo.APP_NAME}?")

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = "Sign up",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    navController.navigate(AppRoutes.Register.route) {
                        popUpTo(AppRoutes.LogIn.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    val fakeLoginViewModel: LoginViewModel = viewModel()
    val navController = rememberNavController()
    
    LoginView(
        loginViewModel = fakeLoginViewModel,
        navController = navController
    )
}