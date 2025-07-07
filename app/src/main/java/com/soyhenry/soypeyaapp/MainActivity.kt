package com.soyhenry.soypeyaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.soyhenry.feature.navigation.ui.MainScreen
import com.soyhenry.library.ui.theme.SoyPeyaAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()

            SoyPeyaAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScreen(navController)
                }
            }
        }
    }
}