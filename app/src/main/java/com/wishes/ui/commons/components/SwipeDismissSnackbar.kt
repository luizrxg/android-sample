package com.wishes.ui.commons.components

import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.MaterialTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeDismissSnackbar(
    data: SnackbarData,
    onDismiss: (() -> Unit)? = null,
    snackbar: @Composable (SnackbarData) -> Unit = {
        Snackbar(
            snackbarData = it,
            contentColor = MaterialTheme.colors.primary,
            containerColor = MaterialTheme.colors.secondary
        )
    },
) {
    val dismissState = rememberDismissState {
        if (it != DismissValue.Default) {
            data.dismiss()
            onDismiss?.invoke()
        }
        true
    }

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
        background = {},
        dismissContent = { snackbar(data) }
    )
}

@Composable
fun SwipeDismissSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = { hostState.currentSnackbarData?.dismiss() },
    snackbar: @Composable (SnackbarData) -> Unit = { data ->
        SwipeDismissSnackbar(
            data = data,
            onDismiss = onDismiss,
        )
    },
) {
    SnackbarHost(
        hostState = hostState,
        snackbar = snackbar,
        modifier = modifier,
    )
}