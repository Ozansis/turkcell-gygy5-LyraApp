package com.turkcell.lyraapp.ui.screens.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.turkcell.lyraapp.ui.components.LyraBnb
import com.turkcell.lyraapp.ui.navigation.BottomNavDestination
import com.turkcell.lyraapp.ui.screens.favorites.FavoritesRoute
import com.turkcell.lyraapp.ui.screens.home.HomeRoute
import com.turkcell.lyraapp.ui.screens.library.LibraryRoute
import com.turkcell.lyraapp.ui.screens.profile.ProfileRoute
import com.turkcell.lyraapp.ui.screens.search.SearchRoute

@Composable
fun MainRoute() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = BottomNavDestination.fromRoute(
        currentBackStackEntry?.destination?.route,
    )

    MainScreen(
        currentDestination = currentDestination,
        onDestinationSelected = { destination ->
            navController.navigate(destination.route) {
                popUpTo(BottomNavDestination.HOME.route) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavDestination.HOME.route,
            modifier = Modifier.padding(paddingValues),
        ) {
            composable(BottomNavDestination.HOME.route) { HomeRoute() }
            composable(BottomNavDestination.SEARCH.route) { SearchRoute() }
            composable(BottomNavDestination.LIBRARY.route) { LibraryRoute() }
            composable(BottomNavDestination.FAVORITES.route) { FavoritesRoute() }
            composable(BottomNavDestination.PROFILE.route) { ProfileRoute() }
        }
    }
}

@Composable
fun MainScreen(
    currentDestination: BottomNavDestination,
    onDestinationSelected: (BottomNavDestination) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            LyraBnb(
                currentDestination = currentDestination,
                onDestinationSelected = onDestinationSelected,
            )
        },
        content = content,
    )
}
