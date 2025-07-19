package com.soyhenry.feature.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.soyhenry.core.approutes.AppRoutes
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.soyhenry.core.session.UserPreferences
import com.soyhenry.feature.cart.ui.CartView
import com.soyhenry.feature.cart.viewmodel.CartViewModel
import com.soyhenry.feature.login.ui.LoginView
import com.soyhenry.feature.login.viewmodel.LoginViewModel
import com.soyhenry.feature.navigation.ui.StartUpView
import com.soyhenry.feature.orders.ui.OrdersView
import com.soyhenry.feature.orders.viewmodel.OrdersViewModel
import com.soyhenry.feature.profile.ui.ProfileView
import com.soyhenry.feature.profile.viewmodel.ProfileViewModel
import com.soyhenry.feature.register.ui.RegisterView
import com.soyhenry.feature.register.viewmodel.RegisterViewModel
import com.soyhenryfeature.products.ui.ProductsView
import com.soyhenryfeature.products.viewmodel.ProductsViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    userPreferences: UserPreferences
) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val productsViewModel: ProductsViewModel = hiltViewModel()
    val registerViewModel: RegisterViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val ordersViewModel: OrdersViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.Products.route,
        modifier = modifier
    ) {
        composable(AppRoutes.StartUp.route) {
            StartUpView(navController, userPreferences)
        }
        composable(AppRoutes.LogIn.route) {
            LoginView(navController, loginViewModel)
        }
        composable(AppRoutes.Register.route) {
            RegisterView(navController, registerViewModel)
        }
        composable(AppRoutes.Products.route) {
            ProductsView(navController, productsViewModel, cartViewModel)
        }
        composable(AppRoutes.Cart.route) {
            CartView(navController, cartViewModel, ordersViewModel)
        }
        composable(AppRoutes.Profile.route) {
            ProfileView(navController, profileViewModel)
        }
        composable(AppRoutes.Orders.route) {
            OrdersView(navController, ordersViewModel)
        }
    }
}
