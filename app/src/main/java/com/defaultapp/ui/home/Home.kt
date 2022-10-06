package com.defaultapp.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavDestination
import com.defaultapp.ui.commons.components.topPadding
import com.defaultapp.ui.navigation.DefaultNavigationDestination
import androidx.compose.material.MaterialTheme

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeRoute(
    onNavigateToDestination: (DefaultNavigationDestination, String) -> Unit,
    currentDestination: NavDestination?,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    HomeScreen(
        onNavigateToDestination,
        onBackClick,
        openDrawer,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDestination: (DefaultNavigationDestination, String) -> Unit,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
) {

    Scaffold(
        contentColor = MaterialTheme.colors.secondary,
        containerColor = MaterialTheme.colors.primary,
        topBar = {},
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.topPadding()
        ) {

        }
    }
}
