package com.soyhenry.soypeyaapp.view.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.*
import com.soyhenry.feature.login.LoginScreen
import com.soyhenry.feature.login.LoginViewModel

class LoginActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                LoginScreen(loginViewModel)
            }
        }
    }
}