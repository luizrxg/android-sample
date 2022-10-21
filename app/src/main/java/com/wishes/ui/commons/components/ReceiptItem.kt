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
import com.wishes.util.formatDayNumberMonthName
import com.wishes.util.formatDotToPeriod
import com.wishes.util.formatHoursMinutes
import java.time.LocalDateTime

@Composable
fun ReceiptItem(
    wish: Wish,
    isFirst: Boolean,
    isLast: Boolean,
    onClick: () -> Unit,
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .requiredHeight(82.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    if (isFirst && isLast) {
                        PaddingValues(0.dp)
                    } else if (isFirst) {
                        PaddingValues(37.dp, 41.dp, 0.dp, 0.dp)
                    } else if (isLast) {
                        PaddingValues(37.dp, 0.dp, 0.dp, 41.dp)
                    } else {
                        PaddingValues(37.dp, 0.dp, 0.dp, 0.dp)
                    }
                )
                .requiredHeight(
                    if (isFirst || isLast)
                        41.dp
                    else if (isFirst && isLast)
                        0.dp
                    else
                        82.dp
                )
                .requiredWidth(1.dp)
                .background(MaterialTheme.colors.secondaryVariant)
                .align(Alignment.CenterStart)
        ){}
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(16.dp, 0.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(0.dp, 0.dp)
            ) {
//                Text(
//                    formatHoursMinutes(wish.data),
//                    style = MaterialTheme.typography.body2,
//                    color = MaterialTheme.colors.secondary,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .padding(end = 16.dp)
//                        .requiredWidth(40.dp)
//                )
                Priority(
                    level = wish.prioridade,
                    modifier = Modifier
                        .requiredSize(44.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colors.secondaryVariant,
                            RoundedCornerShape(100)
                        )
                        .padding(
                            if (wish.prioridade > 2) PaddingValues(14.dp, 13.dp, 12.dp, 12.dp)
                            else PaddingValues(12.dp)
                        )
                )
                Text(
                    wish.nome,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.widthIn(max = 150.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    formatHoursMinutes(wish.data),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.secondaryVariant
                )
                Text(
                    formatDotToPeriod("${ if (wish.prioridade == 3) "+" else "-"} R$ ${wish.preco}"),
                    color =
                        colorResource(
                            if (wish.prioridade == 3) R.color.green
                            else R.color.red
                        ),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    modifier = Modifier.widthIn(max = 150.dp)
                )
            }
        }
    }
}