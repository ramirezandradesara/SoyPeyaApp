package com.soyhenry.feature.navigation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.soyhenry.core.approutes.AppRoutes
import com.soyhenry.feature.navigation.data.model.BottomNavItem
import com.soyhenry.feature.navigation.graph.NavGraph

@Composable
fun MainScreen(navController: NavHostController) {
    val bottomNavItems = listOf(
        BottomNavItem(Icons.Filled.Home, "Products", AppRoutes.Products),
        BottomNavItem(Icons.Filled.ShoppingCart, "Cart", AppRoutes.Cart),
        BottomNavItem(Icons.Filled.Person, "Profile", AppRoutes.Profile)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = when (currentDestination?.route) {
        AppRoutes.Cart.route,
        AppRoutes.Profile.route,
        AppRoutes.Products.route -> true
        else -> false
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    items = bottomNavItems,
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
