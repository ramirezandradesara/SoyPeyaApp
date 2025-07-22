package com.soyhenry.library.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
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
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}