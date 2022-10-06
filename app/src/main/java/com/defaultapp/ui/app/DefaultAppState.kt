package com.defaultapp.ui.app

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.tracing.trace
import com.defaultapp.R
import com.defaultapp.ui.home.HomeDestination
import com.defaultapp.ui.navigation.Icon
import com.defaultapp.ui.navigation.DefaultNavigationDestination
import com.defaultapp.ui.navigation.TopLevelDestination
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberDefaultAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberAnimatedNavController()
): DefaultAppState {

    return remember(navController, windowSizeClass) {
        DefaultAppState(navController, windowSizeClass)
    }
}

@Stable
class DefaultAppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination


    val shouldShowBottomBar: Boolean
        @Composable
        get() = (
                windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
                windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact
            )

    val shouldShowNavRail: Boolean
        @Composable get() = !shouldShowBottomBar

    val topLevelDestinations: List<TopLevelDestination> = listOf(
        TopLevelDestination(
            HomeDestination.route,
            HomeDestination.destination,
            Icon.ImageVectorIcon(Icons.Rounded.Home),
            Icon.ImageVectorIcon(Icons.Rounded.Home),
            R.string.home_title
        )
    )

    fun navigate(destination: DefaultNavigationDestination, route: String? = null) {
        trace("Navigation: $destination") {
            if (destination is TopLevelDestination) {
                navController.navigate(route ?: destination.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            } else {
                navController.navigate(route ?: destination.route)
            }
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}
