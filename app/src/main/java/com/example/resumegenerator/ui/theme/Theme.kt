package com.example.resumegenerator.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = CVAppColors.Light.primary,
    onPrimary = CVAppColors.Light.onPrimary,
    primaryContainer = CVAppColors.Light.primaryVariant,
    secondary = CVAppColors.Light.secondary,
    onSecondary = CVAppColors.Light.onSecondary,
    secondaryContainer = CVAppColors.Light.secondaryVariant,
    background = CVAppColors.Light.background,
    onBackground = CVAppColors.Light.onBackground,
    surface = CVAppColors.Light.surface,
    onSurface = CVAppColors.Light.onSurface,
    error = CVAppColors.Light.error,
    onError = CVAppColors.Light.onError,
    outline = CVAppColors.Light.divider
)

private val DarkColorScheme = darkColorScheme(
    primary = CVAppColors.Dark.primary,
    onPrimary = CVAppColors.Dark.onPrimary,
    primaryContainer = CVAppColors.Dark.primaryVariant,
    secondary = CVAppColors.Dark.secondary,
    onSecondary = CVAppColors.Dark.onSecondary,
    secondaryContainer = CVAppColors.Dark.secondaryVariant,
    background = CVAppColors.Dark.background,
    onBackground = CVAppColors.Dark.onBackground,
    surface = CVAppColors.Dark.surface,
    onSurface = CVAppColors.Dark.onSurface,
    error = CVAppColors.Dark.error,
    onError = CVAppColors.Dark.onError,
    outline = CVAppColors.Dark.divider
)

@Composable
fun ResumeGeneratorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) DarkColorScheme else LightColorScheme
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}