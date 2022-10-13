package com.wishes.ui.commons.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun InputDialog(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    cancelText: String,
    confirmText: String,
    confirmAction: () -> Unit,
    onDismiss: () -> Unit,
){
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(MaterialTheme.colors.background, RoundedCornerShape(10))
                .border(.5.dp, MaterialTheme.colors.secondary, RoundedCornerShape(10))
        ){
            Text(
                title,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(100)),
                .5.dp,
                MaterialTheme.colors.secondaryVariant,
            )
            TextField(
                value = value,
                onValueChange = { onValueChange(it) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 16.dp)
            ){
                Button(
                    text = cancelText,
                    onClick = { onDismiss() },
                    variant = "text",
                    modifier = Modifier
                        .weight(.5f)
                        .padding(end = 8.dp)
                        .requiredHeight(56.dp)
                )
                Button(
                    text = confirmText,
                    onClick = {
                        confirmAction()
                        onDismiss()
                    },
                    variant = "filled",
                    modifier = Modifier
                        .weight(.5f)
                        .requiredHeight(56.dp)
                )
            }
        }
    }
}