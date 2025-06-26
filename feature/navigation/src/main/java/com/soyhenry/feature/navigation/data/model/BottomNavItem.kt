package com.soyhenry.feature.navigation.data.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.soyhenry.core.approutes.AppRoutes

data class BottomNavItem(
    val icon: ImageVector,
    val label: String,
    val route: AppRoutes
)
