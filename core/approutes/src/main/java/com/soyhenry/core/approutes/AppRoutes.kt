package com.soyhenry.core.approutes

sealed class AppRoutes(val route: String) {
    object Home : AppRoutes("home")
    object LogIn : AppRoutes("login")
    object Register : AppRoutes("register")

    data class Profile(val baseRoute: String = "profile", val argName: String = "userId") :
        AppRoutes("$baseRoute/{$argName}") {
        fun createRoute(userId: String) = "$baseRoute/$userId"
    }
}