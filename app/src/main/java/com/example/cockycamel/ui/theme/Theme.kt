package com.example.cockycamel.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = OrangePrimary,
    onPrimary = White,
    secondary = OrangeSecondary,
    onSecondary = DarkText,
    tertiary = OrangeDark,
    background = OrangeBackground,
    onBackground = DarkText,
    surface = OrangeSurface,
    onSurface = DarkText
)

private val DarkColorScheme = darkColorScheme(
    primary = OrangeSecondary,
    onPrimary = DarkText,
    secondary = OrangePrimary,
    tertiary = OrangeDark,
    background = Color(0xFF1E1E1E),
    onBackground = OrangeBackground,
    surface = Color(0xFF2C2C2C),
    onSurface = OrangeBackground
)

@Composable
fun CockyCamelTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}