package com.soyhenry.feature.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.soyhenry.core.approutes.AppRoutes
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.soyhenry.feature.login.ui.LoginView
import com.soyhenry.feature.login.viewmodel.LoginViewModel
import com.soyhenry.feature.register.ui.RegisterView
import com.soyhenry.feature.register.viewmodel.RegisterViewModel
import com.soyhenryfeature.products.ui.ProductsView
import com.soyhenryfeature.products.viewmodel.ProductsViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val productsViewModel: ProductsViewModel = hiltViewModel()
    val registerViewModel: RegisterViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.LogIn.route,
        modifier = modifier
    ) {
        composable(AppRoutes.LogIn.route) {
            LoginView(navController, loginViewModel)
        }
        composable(AppRoutes.Register.route) {
            RegisterView(navController, registerViewModel)
        }
        composable(AppRoutes.Products.route) {
            ProductsView(navController, productsViewModel)
        }
    }
}
