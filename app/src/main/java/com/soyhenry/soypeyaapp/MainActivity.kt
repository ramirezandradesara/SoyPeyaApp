package com.soyhenry.soypeyaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.soyhenry.core.session.UserPreferences
import com.soyhenry.data.workers.ProductSyncManager
import com.soyhenry.feature.navigation.ui.MainScreen
import com.soyhenry.library.ui.theme.SoyPeyaAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var userPreferences: UserPreferences
    @Inject
    lateinit var productSyncManager: ProductSyncManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productSyncManager.schedulePeriodicProductSync()
        productSyncManager.syncNow()

        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()

            SoyPeyaAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScreen( navController, userPreferences)
                }
            }
        }
    }
}