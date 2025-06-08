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
import com.soyhenry.core.approutes.AppRoutes
import com.soyhenry.feature.register.ui.RegisterView
import com.soyhenryfeature.products.ui.ProductsView

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()

            Surface(color = MaterialTheme.colorScheme.background) {
                NavHost(
                    navController = navController,
                    startDestination = AppRoutes.Products.route
                ) {
                    composable(route =  AppRoutes.LogIn.route) {
                        LoginView(
                            loginViewModel = loginViewModel,
                            navController = navController
                        )
                    }
                    composable(route = AppRoutes.Home.route) {
                        HomeView()
                    }
                    composable(route = AppRoutes.Register.route) {
                        RegisterView(
                            navController = navController
                        )
                    }
                    composable(route = AppRoutes.Products.route) {
                        ProductsView(navController = navController)
                    }
                }
            }
        }
    }
}