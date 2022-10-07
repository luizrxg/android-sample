package com.wishes.ui.home

import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.wishes.ui.navigation.WishesNavigationDestination
import com.google.accompanist.navigation.animation.composable

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
) {
    composable(
        route = HomeDestination.route,
    ) {
        HomeRoute(
            onNavigateToDestination,
            currentDestination,
            onBackClick,
            openDrawer,
        )
    }
}