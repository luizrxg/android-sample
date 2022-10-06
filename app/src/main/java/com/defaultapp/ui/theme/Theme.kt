package com.defaultapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.defaultapp.ui.theme.*

private val DarkColorPalette = darkColors(
    primary = DarkPrimaryColor,
    secondaryVariant = DarkSecondaryVariantColor,
    secondary = DarkSecondaryColor,
    primaryVariant = DarkPrimaryVariantColor,
    background = DarkBackgroundColor,
)

private val ColorPalette = lightColors(
    primary = LightPrimaryColor,
    secondaryVariant = LightSecondaryVariantColor,
    secondary = LightSecondaryColor,
    primaryVariant = LightPrimaryVariantColor,
    background = LightBackgroundColor,
)

@Composable
fun DefaultTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else ColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}