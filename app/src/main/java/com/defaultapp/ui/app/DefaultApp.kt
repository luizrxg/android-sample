package com.defaultapp.ui.app

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.runtime.rememberCoroutineScope
import com.defaultapp.ui.navigation.DefaultNavHost
import com.defaultapp.ui.theme.DefaultTheme
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun DefaultApp(
    windowSizeClass: WindowSizeClass,
    appState: DefaultAppState = rememberDefaultAppState(windowSizeClass)
) {
    val state = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    DefaultTheme {
        Scaffold(
            scaffoldState = state,
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .navigationBarsPadding(),
            backgroundColor = MaterialTheme.colors.primary,
            drawerContent = {  },
            drawerScrimColor = MaterialTheme.colors.primary.copy(.7f),
            drawerBackgroundColor = MaterialTheme.colors.primary,
            drawerGesturesEnabled = state.drawerState.isOpen,
            bottomBar = {
                if (appState.shouldShowBottomBar){

                }
            }
        ) { padding ->
            DefaultNavHost(
                navController = appState.navController,
                onNavigateToDestination = appState::navigate,
                currentDestination = appState.currentDestination,
                onBackClick = appState::onBackClick,
                openDrawer = { scope.launch { state.drawerState.open() } },
                modifier = Modifier
                    .padding(padding)
                    .consumedWindowInsets(padding)
                    .navigationBarsPadding()
            )
        }
    }
}