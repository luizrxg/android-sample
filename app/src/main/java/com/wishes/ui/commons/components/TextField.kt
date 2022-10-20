package com.wishes.ui.commons.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wishes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    value: String,
    onValueChange: (value: String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    placeholder: String? = null,
    maxLength: Int? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier,
){
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (
                if(maxLength != null){
                    it.length < maxLength
                } else true
            )
            onValueChange(it)
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
            containerColor = MaterialTheme.colors.onBackground,
            textColor = MaterialTheme.colors.secondary,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.secondary,
        )
    )
}