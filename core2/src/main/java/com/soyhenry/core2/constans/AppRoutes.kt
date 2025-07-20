package com.soyhenry.core2.constans

sealed class AppRoutes(val route: String) {
    object StartUp : AppRoutes("start_up")
    object LogIn : AppRoutes("login")
    object Register : AppRoutes("register")
    object Products : AppRoutes("products")
    object Cart : AppRoutes("cart")
    object Profile : AppRoutes("profile")
    object Orders : AppRoutes("orders")

    /* data class Profile(val baseRoute: String = "profile", val argName: String = "userId") :
        AppRoutes("$baseRoute/{$argName}") {
        fun createRoute(userId: String) = "$baseRoute/$userId"
    } */
}