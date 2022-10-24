package com.wishes.ui.simulation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.wishes.ui.navigation.WishesNavigationDestination

object SimulationDestination : WishesNavigationDestination {
    override val route = "simulation_route"
    override val destination = "simulation_destination"
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.simulation(
    onNavigateToDestination: (WishesNavigationDestination, String) -> Unit,
    currentDestination: NavDestination?,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
) {
    composable(
        route = SimulationDestination.route,
    ) {
        SimulationRoute(
            onNavigateToDestination,
            currentDestination,
            onBackClick,
            openDrawer,
        )
    }
}