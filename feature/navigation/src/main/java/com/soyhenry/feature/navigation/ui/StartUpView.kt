package com.soyhenry.feature.navigation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.soyhenry.core.constants.AppRoutes
import com.soyhenry.core.session.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun StartUpView(navController: NavHostController, userPreferences: UserPreferences) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            val user = userPreferences.user.first()
            if (user != null) {
                navController.navigate(AppRoutes.Products.route) {
                    popUpTo(AppRoutes.StartUp.route) { inclusive = true }
                }
            } else {
                navController.navigate(AppRoutes.LogIn.route) {
                    popUpTo(AppRoutes.StartUp.route) { inclusive = true }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
