package com.soyhenry.library.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = RedPrimary,
    secondary = GoldPrimary,
    surface = LightSurface,
    onSurface = LightOnSurface,
    background = LightSurface,
    onPrimary = Color.White,
    onSecondary = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkRedPrimary,
    secondary = DarkGoldPrimary,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    background = DarkSurface,
    onPrimary = Color.White,
    onSecondary = Color.Black
)

@Composable
fun SoyPeyaAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        /*dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        } */

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}