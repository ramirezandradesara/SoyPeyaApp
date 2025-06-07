package com.soyhenry.soypeyaapp.view.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.soyhenry.feature.login.ui.LoginView
import com.soyhenry.feature.login.viewmodel.LoginViewModel
import com.soyhenry.feature.home.HomeView
import androidx.activity.viewModels
import androidx.navigation.NavHostController

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()

            Surface(color = MaterialTheme.colorScheme.background) {
                NavHost(navController = navController, startDestination = "login") {
                    composable(route = "login") {
                        LoginView(
                            loginViewModel = loginViewModel,
                            navController = navController
                        )
                    }
                    composable(route = "home") {
                        HomeView()
                    }
                }
            }
        }
    }
}