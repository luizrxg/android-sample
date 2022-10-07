package com.wishes.ui.app

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.runtime.rememberCoroutineScope
import com.wishes.ui.navigation.WishesNavHost
import com.wishes.ui.theme.WishesTheme
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun WishesApp(
    windowSizeClass: WindowSizeClass,
    appState: WishesAppState = rememberWishesAppState(windowSizeClass)
) {
    val state = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    WishesTheme {
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
            WishesNavHost(
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