package com.wishes.ui.receipt

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import com.wishes.ui.navigation.WishesNavigationDestination
import com.google.accompanist.navigation.animation.composable
import com.wishes.ui.home.HomeDestination

object ReceiptDestination : WishesNavigationDestination {
    override val route = "receipt_route"
    override val destination = "receipt_destination"

}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.receipt(
    onNavigateToDestination: (WishesNavigationDestination, String) -> Unit,
    currentDestination: NavDestination?,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
    onNavigateToOverview: (Long) -> Unit,
) {
    composable(
        route = ReceiptDestination.route,
    ) {
        ReceiptRoute(
            onNavigateToDestination,
            currentDestination,
            onBackClick,
            openDrawer,
            onNavigateToOverview
        )
    }
}