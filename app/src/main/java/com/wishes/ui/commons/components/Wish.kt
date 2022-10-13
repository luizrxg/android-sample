package com.wishes.ui.commons.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wishes.data.model.Wish
import com.wishes.database.entity.WishEntity
import com.wishes.R

@Composable
fun Wish(
    wish: Wish,
    onClick: () -> Unit,
){
    @Composable
    fun getPriorityColor(): Color? {
        return when(wish.prioridade){
            0 -> { MaterialTheme.colors.primary }
            1 -> { MaterialTheme.colors.primary }
            2 -> { MaterialTheme.colors.background }
            3 -> { colorResource(R.color.green) }
            4 -> { colorResource(R.color.red) }
            else -> { null }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 16.dp)
                .clip(MaterialTheme.shapes.small)
                .clickable { onClick() }
                .background(colorResource(R.color.dark))
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    wish.nome,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.widthIn(max = 150.dp)
                )
                Text(
                    "R$ ${wish.preco}",
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.widthIn(max = 150.dp)
                )
            }
            Icon(
                painter = painterResource(
                    when (wish.prioridade){
                        0 -> { R.drawable.ic_arrow }
                        1 -> { R.drawable.ic_arrow2 }
                        2 -> { R.drawable.ic_arrow3 }
                        3 -> { R.drawable.ic_more_cash }
                        4 -> { R.drawable.ic_minus_cash }
                        else -> { 0 }
                    }
                ),
                contentDescription = null,
                tint =
                    if (wish.prioridade != 2)
                        MaterialTheme.colors.primary
                    else
                        MaterialTheme.colors.background,
                modifier = Modifier
                    .requiredSize(38.dp)
                    .background(
                        if (wish.prioridade == 2)
                            MaterialTheme.colors.primary
                        else
                            Color.Transparent,
                        RoundedCornerShape(100)
                    )
                    .border(
                        2.dp,
                        if (wish.prioridade != 0)
                            MaterialTheme.colors.primary
                        else
                            Color.Transparent,
                        RoundedCornerShape(100)
                    )
                    .padding(8.dp)
            )
        }
    }
}