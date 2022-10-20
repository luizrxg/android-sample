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
import com.wishes.util.formatDotToPeriod

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
                .background(MaterialTheme.colors.onBackground)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    wish.nome,
                    color = MaterialTheme.colors.secondaryVariant,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.widthIn(max = 150.dp)
                )
                Text(
                    formatDotToPeriod("R$ ${wish.preco}"),
                    color = MaterialTheme.colors.secondary,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.widthIn(max = 150.dp)
                )
            }
            Priority(
                level = wish.prioridade,
                modifier = Modifier.requiredSize(44.dp)
            )
        }
    }
}