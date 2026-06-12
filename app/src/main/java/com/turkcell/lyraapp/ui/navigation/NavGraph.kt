package com.turkcell.lyraapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turkcell.lyraapp.ui.screens.login.LoginRoute
import com.turkcell.lyraapp.ui.screens.register.RegisterRoute

private object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
}

@Composable
fun LyraNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN,
        modifier = modifier,
    ) {
        composable(Routes.LOGIN) {
            LoginRoute(
                onNavigateToHome = { /* TODO: Ana sayfa bağlandığında doldurulacak */ },
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) },
                onNavigateToForgotPassword = { /* TODO */ },
            )
        }
        composable(Routes.REGISTER) {
            RegisterRoute(
                onNavigateToHome = { /* TODO: Ana sayfa bağlandığında doldurulacak */ },
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateUp = { navController.navigateUp() },
            )
        }
    }
}
