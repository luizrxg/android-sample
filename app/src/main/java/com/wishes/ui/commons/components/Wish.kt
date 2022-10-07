package com.wishes.ui.commons.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wishes.data.model.Wish
import com.wishes.database.entity.WishEntity
import com.wishes.R

@Composable
fun Wish(
    wish: Wish
){
    Box{
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.Black.copy(.2f), MaterialTheme.shapes.small)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column() {
                Text(
                    wish.nome,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.widthIn(max = 150.dp)
                )
                Text(
                    "R$ ${wish.preco}",
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.widthIn(max = 150.dp)
                )
            }
        }

        if (wish.comprado)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background.copy(.2f))
        ){}
    }
}