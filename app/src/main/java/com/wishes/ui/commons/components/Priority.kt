package com.wishes.ui.commons.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
    )
}