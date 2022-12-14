package com.wishes.ui.create

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import com.wishes.ui.navigation.WishesNavigationDestination
import com.google.accompanist.navigation.animation.composable

object CreateDestination : WishesNavigationDestination {
    override val route = "create_route"
    override val destination = "create_destination"
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.create(
    onNavigateToDestination: (WishesNavigationDestination, String) -> Unit,
    currentDestination: NavDestination?,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
) {
    composable(
        route = CreateDestination.route,
    ) {
        CreateRoute(
            onNavigateToDestination,
            currentDestination,
            onBackClick,
            openDrawer,
        )
    }
}