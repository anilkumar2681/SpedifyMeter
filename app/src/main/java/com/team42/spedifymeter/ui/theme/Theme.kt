package com.team42.spedifymeter.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Teal200,
    //primaryVariant = Purple700,
    secondary = Pink,
    background = DarkColor,
    surface = DarkColor2,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onSurface = LightColor2,
    onBackground = LightColor2
)

private val LightColorScheme = lightColorScheme(
    primary = Teal200,
    //primaryVariant = Teal200,
    secondary = Pink,
    background = Color(0xFFF7F8FA),
    surface = Color.White,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onSurface = Color(0xFF1B1D22),
    onBackground = Color(0xFF1B1D22)
)

@Composable
fun SpedifyMeterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}