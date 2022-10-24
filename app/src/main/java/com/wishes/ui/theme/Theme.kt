package com.wishes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = DarkPrimaryColor,
    secondaryVariant = DarkSecondaryVariantColor,
    secondary = DarkSecondaryColor,
    primaryVariant = DarkPrimaryVariantColor,
    background = DarkBackgroundColor,
    onBackground = DarkOnBackgroundColor,
    onPrimary = OnPrimaryColor
)

private val ColorPalette = lightColors(
    primary = LightPrimaryColor,
    secondaryVariant = LightSecondaryVariantColor,
    secondary = LightSecondaryColor,
    primaryVariant = LightPrimaryVariantColor,
    background = LightBackgroundColor,
    onBackground = LightOnBackgroundColor,
    onPrimary = OnPrimaryColor
)

@Composable
fun WishesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else ColorPalette
//    val colors = DarkColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}