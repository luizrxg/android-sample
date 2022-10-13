package com.wishes.ui.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.wishes.ui.create.create
import com.wishes.ui.home.HomeDestination
import com.wishes.ui.home.home
import com.wishes.ui.overview.OverviewDestination
import com.wishes.ui.overview.overview
import com.wishes.ui.receipt.receipt

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WishesNavHost(
    navController: NavHostController,
    currentDestination: NavDestination?,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
    onNavigateToDestination: (WishesNavigationDestination, String) -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = HomeDestination.route
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { defaultWishesEnterTransition(initialState, targetState) },
        exitTransition = { defaultWishesExitTransition(initialState, targetState) },
        popEnterTransition = { defaultWishesPopEnterTransition() },
        popExitTransition = { defaultWishesPopExitTransition() },
        modifier = modifier,
    ) {
        home(
            onNavigateToDestination,
            currentDestination,
            onBackClick,
            openDrawer,
            { id ->
                onNavigateToDestination(
                    OverviewDestination, OverviewDestination.createNavigationRoute(id)
                )
            },
        )
        create(
            onNavigateToDestination,
            currentDestination,
            onBackClick,
            openDrawer,
        )
        overview(
            onNavigateToDestination,
            currentDestination,
            onBackClick,
            openDrawer,
        )
        receipt(
            onNavigateToDestination,
            currentDestination,
            onBackClick,
            openDrawer,
            { id ->
                onNavigateToDestination(
                    OverviewDestination, OverviewDestination.createNavigationRoute(id)
                )
            },
        )
    }
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultWishesEnterTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): EnterTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeIn()
    }
    // Otherwise we're in the same nav graph, we can imply a direction
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.Start)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultWishesExitTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): ExitTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeOut()
    }
    // Otherwise we're in the same nav graph, we can imply a direction
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.Start)
}

private val NavDestination.hostNavGraph: NavGraph
    get() = hierarchy.first { it is NavGraph } as NavGraph

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultWishesPopEnterTransition(): EnterTransition {
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.End)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultWishesPopExitTransition(): ExitTransition {
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.End)
}