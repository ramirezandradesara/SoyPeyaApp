package com.soyhenry.feature.navigation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.soyhenry.feature.navigation.data.model.BottomNavItem

@Composable
fun BottomNavBar(
    items: List<BottomNavItem>,
    navController: NavController,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        tonalElevation = 4.dp,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        items.forEach { item ->
            val selected = currentDestination?.hierarchy?.any {
                it.route == item.route.route
            } == true

            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
                }
            )
        }
    }
}
