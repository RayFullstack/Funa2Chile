package com.funa2chile.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.funa2chile.app.ui.screens.home.HomeScreen
import com.funa2chile.app.ui.screens.login.LoginScreen
import com.funa2chile.app.ui.screens.registro.RegistroScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginExitoso = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onIrARegistro = { navController.navigate(Routes.REGISTRO) }
            )
        }
        composable(Routes.REGISTRO) {
            RegistroScreen(
                onRegistroExitoso = { navController.popBackStack() },
                onVolver = { navController.popBackStack() }
            )
        }
        composable(Routes.HOME) { HomeScreen() }
    }
}
