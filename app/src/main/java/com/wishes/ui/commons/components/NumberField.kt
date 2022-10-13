package com.wishes.ui.commons.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberField(
    value: BigDecimal,
    onValueChange: (value: BigDecimal) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = value.toString(),
        onValueChange = {
            if (it.length < 20)
                onValueChange(
                    if (it.isNotEmpty())
                        it.toBigDecimal()
                    else
                        0.toBigDecimal()
                )
            },
        placeholder = {
            if (placeholder != null)
            Text(
                placeholder,
                color = MaterialTheme.colors.secondary.copy(.2f)
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        textStyle = MaterialTheme.typography.body1,
        singleLine = true,
        shape = MaterialTheme.shapes.small,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = MaterialTheme.colors.background,
            textColor = MaterialTheme.colors.secondary,
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.secondary,
        )
    )
}