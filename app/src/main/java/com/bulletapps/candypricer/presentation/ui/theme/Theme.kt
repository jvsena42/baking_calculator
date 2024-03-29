package com.bulletapps.candypricer.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = primary,
    primaryVariant = primaryDark,
    secondary = colorAccent,
    background = whiteBackground
)

private val LightColorPalette = lightColors(
    primary = primary,
    primaryVariant = primaryDark,
    secondary = colorAccent,
    background = whiteBackground
)

@Composable
fun CandyPricerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}