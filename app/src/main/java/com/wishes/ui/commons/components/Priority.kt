package com.wishes.ui.commons.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wishes.R

@Composable
fun Priority(
    level: Int,
    modifier: Modifier = Modifier
){
    Image(
        painter = painterResource(
            when (level){
                0 -> { R.drawable.ic_priority_1 }
                1 -> { R.drawable.ic_priority_2 }
                2 -> { R.drawable.ic_priority_3 }
                3 -> { R.drawable.ic_more_cash }
                4 -> { R.drawable.ic_minus_cash }
                else -> { 0 }
            }
        ),
        contentDescription = null,
        modifier = modifier
            .padding(8.dp)
    )
}