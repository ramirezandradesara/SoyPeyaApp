package com.soyhenry.core.approutes

sealed class AppRoutes(val route: String) {
    object LogIn : AppRoutes("login")
    object Register : AppRoutes("register")
    object Products : AppRoutes("products")
    object Cart : AppRoutes("cart")
    object Profile : AppRoutes("profile")

    /* data class Profile(val baseRoute: String = "profile", val argName: String = "userId") :
        AppRoutes("$baseRoute/{$argName}") {
        fun createRoute(userId: String) = "$baseRoute/$userId"
    } */
}