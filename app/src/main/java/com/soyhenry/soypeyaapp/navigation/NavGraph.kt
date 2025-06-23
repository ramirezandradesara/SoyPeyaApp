package com.soyhenry.soypeyaapp.navigation

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.soyhenry.core.approutes.AppRoutes
import com.soyhenry.feature.home.HomeView
import com.soyhenry.feature.login.ui.LoginView
import com.soyhenry.feature.login.viewmodel.LoginViewModel
import com.soyhenry.feature.register.ui.RegisterView
import com.soyhenryfeature.products.ui.ProductsView
import com.soyhenryfeature.products.viewmodel.ProductsViewModel

@Composable
fun NavGraph(navController : NavHostController) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val productsViewModel: ProductsViewModel  = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.LogIn.route
    ) {
        composable(route = AppRoutes.LogIn.route) {
            LoginView(
                loginViewModel = loginViewModel,
                navController = navController
            )
        }
        composable(route = AppRoutes.Register.route) {
            RegisterView(navController = navController)
        }
        composable(route = AppRoutes.Home.route) {
            HomeView()
        }
        composable(route = AppRoutes.Products.route) {
            ProductsView(
                navController = navController,
                viewModel = productsViewModel
            )
        }
    }
}