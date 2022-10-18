package com.wishes.ui.commons.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wishes.R

@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
    variant: String,
    enabled: Boolean? = true,
    customColor: Color? = null,
    modifier: Modifier = Modifier,
){
    val color = customColor ?: MaterialTheme.colors.primary

    Button(
        onClick = { onClick() },
        colors =
        when (variant){
            "filled" -> {
                if (enabled!!){
                    ButtonDefaults.buttonColors(
                        containerColor = color,
                        contentColor = MaterialTheme.colors.secondary,
                    )
                } else {
                    ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.dark),
                        contentColor = MaterialTheme.colors.background,
                    )
                }
            }
            "translucent" -> {
                if (enabled!!){
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Black.copy(.2f),
                        contentColor = MaterialTheme.colors.secondary,
                    )
                } else {
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colors.secondary,
                        contentColor = colorResource(R.color.dark),
                    )
                }
            }
            "contained" -> {
                if (enabled!!){
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = color,
                    )
                } else {
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = colorResource(R.color.dark),
                    )
                }
            }
            "text" -> {
                if (enabled!!){
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colors.secondary,
                    )
                } else {
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = colorResource(R.color.dark),
                    )
                }
            }
            else -> ButtonDefaults.buttonColors()
        },
        shape = MaterialTheme.shapes.small,
        border =
            if (variant == "contained") {
                BorderStroke(
                    2.dp,
                    if (enabled!!)
                        color
                    else
                        colorResource(R.color.dark)
                )
            }
            else null,
        modifier = modifier
    ) {
        Text(
            text,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.button
        )
    }
}