package com.wishes.ui.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.wishes.ui.navigation.WishesNavigationDestination

object HomeDestination : WishesNavigationDestination {
    override val route = "home_route"
    override val destination = "home_destination"

}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.home(
    onNavigateToDestination: (WishesNavigationDestination, String) -> Unit,
    currentDestination: NavDestination?,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
    onNavigateToOverview: (Long) -> Unit,
) {
    composable(
        route = HomeDestination.route,
    ) {
        HomeRoute(
            onNavigateToDestination,
            currentDestination,
            onBackClick,
            openDrawer,
            onNavigateToOverview
        )
    }
}