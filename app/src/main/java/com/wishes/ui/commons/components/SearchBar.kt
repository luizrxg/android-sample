package com.wishes.ui.commons.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
    placeholder: String? = null,
    label: @Composable (() -> Unit)? = null,
    variant: String,
    modifier: Modifier = Modifier
){
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            if (isFocused){
                IconButton(
                    onClick = { focusManager.clearFocus() },
                    modifier = Modifier.requiredSize(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier.requiredSize(24.dp)
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier.requiredSize(24.dp)
                )
            }
        },
        trailingIcon = {
            if (value.isNotEmpty()){
                IconButton(
                    onClick = onClear,
                    modifier = Modifier.requiredSize(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier.requiredSize(24.dp)
                    )
                }
            }
        },
        placeholder = placeholder,
        label = label,
        variant = variant,
        modifier = modifier
            .onFocusChanged { focusState -> isFocused = focusState.isFocused }
    )
}