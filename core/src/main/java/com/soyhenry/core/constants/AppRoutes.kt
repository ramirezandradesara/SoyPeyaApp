package com.soyhenry.core.constants

sealed class AppRoutes(val route: String) {
    object StartUp : AppRoutes("start_up")
    object LogIn : AppRoutes("login")
    object Register : AppRoutes("register")
    object Products : AppRoutes("products")
    object Cart : AppRoutes("cart")
    object Profile : AppRoutes("profile")
    object Orders : AppRoutes("orders")
}