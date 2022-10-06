package com.defaultapp.ui.home

import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.defaultapp.ui.navigation.DefaultNavigationDestination
import com.google.accompanist.navigation.animation.composable

object HomeDestination : DefaultNavigationDestination {
    const val id = "arg"
    override val route = "home_route/{$id}"
    override val destination = "home_destination"

    fun createNavigationRoute(id: Long): String {
        val idArg = Uri.encode("$id")
        return "home_route/$idArg"
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.home(
    onNavigateToDestination: (DefaultNavigationDestination, String) -> Unit,
    currentDestination: NavDestination?,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
) {
    composable(
        route = HomeDestination.route,
        arguments = listOf(
            navArgument(HomeDestination.id) {
                type = NavType.LongType
            },
        )
    ) {
        HomeRoute(
            onNavigateToDestination,
            currentDestination,
            onBackClick,
            openDrawer,
        )
    }
}