package com.soyhenry.soypeyaapp.view.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.soyhenry.feature.login.LoginView
import com.soyhenry.feature.login.LoginViewModel
import com.soyhenry.soypeyaapp.ui.theme.SoyPeyaAppTheme
import androidx.activity.viewModels

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoyPeyaAppTheme {
                LoginView(loginViewModel = loginViewModel)
            }
        }
    }
}