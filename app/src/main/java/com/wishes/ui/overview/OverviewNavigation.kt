package com.wishes.ui.overview

import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.wishes.ui.navigation.WishesNavigationDestination
import com.google.accompanist.navigation.animation.composable

object OverviewDestination : WishesNavigationDestination {
    const val id = "id"
    override val route = "overview_route/{$id}"
    override val destination = "overview_destination"

    fun createNavigationRoute(id: Long): String {
        val idArg = Uri.encode("$id")
        return "overview_route/$idArg"
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.overview(
    onNavigateToDestination: (WishesNavigationDestination, String) -> Unit,
    currentDestination: NavDestination?,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
) {
    composable(
        route = OverviewDestination.route,
        arguments = listOf(
            navArgument(OverviewDestination.id) {
                type = NavType.LongType
            }
        )
    ) {
        OverviewRoute(
            onNavigateToDestination,
            currentDestination,
            onBackClick,
            openDrawer,
        )
    }
}