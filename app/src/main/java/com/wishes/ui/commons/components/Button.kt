package com.wishes.ui.commons.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wishes.R

@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
    variant: String,
    box: Boolean? = false,
    painterIcon: Painter? = null,
    imageVectorIcon: ImageVector? = null,
    enabled: Boolean? = true,
    customColor: Color? = null,
    modifier: Modifier = Modifier,
){
    val color = customColor ?: MaterialTheme.colors.primary

    if (box == true){
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier
                .requiredSize(98.dp)
                .clip(MaterialTheme.shapes.small)
                .clickable { onClick() }
                .background(
                    when (variant){
                        "filled" -> { MaterialTheme.colors.primary }
                        "translucent" -> { Color.Black.copy(.2f) }
                        "contained" -> { Color.Transparent }
                        "text-white" -> { Color.Transparent }
                        "text-primary" -> { Color.Transparent }
                        else -> MaterialTheme.colors.primary
                    },
                    MaterialTheme.shapes.small
                )
                .padding(16.dp)
        ) {
            if (painterIcon != null) {
                Icon(
                    painter = painterIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .requiredSize(24.dp)
                )
            } else if (imageVectorIcon != null) {
                Icon(
                    imageVector = imageVectorIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .requiredSize(24.dp)
                )
            }
            Text(
                text,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.button,
                fontSize = 10.sp,
                overflow = TextOverflow.Ellipsis,
                color =
                    when (variant){
                        "filled" -> { MaterialTheme.colors.secondary }
                        "translucent" -> { MaterialTheme.colors.secondary }
                        "contained" -> { color }
                        "text-white" -> { MaterialTheme.colors.secondary }
                        "text-primary" -> { MaterialTheme.colors.primary }
                        else -> MaterialTheme.colors.primary
                    }
            )
        }
    } else {
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
                            containerColor = MaterialTheme.colors.onBackground,
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
                            contentColor = MaterialTheme.colors.onBackground,
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
                            contentColor = MaterialTheme.colors.onBackground,
                        )
                    }
                }
                "text-white" -> {
                    if (enabled!!){
                        ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colors.secondary,
                        )
                    } else {
                        ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colors.onBackground,
                        )
                    }
                }
                "text-primary" -> {
                    if (enabled!!){
                        ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colors.primary,
                        )
                    } else {
                        ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colors.onBackground,
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
                        MaterialTheme.colors.onBackground
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
}