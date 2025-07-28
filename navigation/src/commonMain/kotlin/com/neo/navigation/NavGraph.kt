package com.neo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neo.auth.AuthScreen
import com.neo.home.HomeGraphScreen
import com.neo.profile.ProfileScreen
import com.neo.shared.navigation.Screen

@Composable
fun SetupNavGraph(startDestination: Screen = Screen.Auth) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Auth> {
            AuthScreen(
                navigateToHome = {
                    navController.navigate(Screen.HomeGraph) {
                        popUpTo(Screen.Auth) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Screen.HomeGraph> {
            HomeGraphScreen(
                navigateToAuth = {
                    navController.navigate(Screen.Auth) {
                        popUpTo(Screen.HomeGraph) {
                            inclusive = true
                        }
                    }
                },
                navigateToProfile = {
                    navController.navigate(Screen.Profile)
                }
            )
        }

        composable<Screen.Profile> {
            ProfileScreen()
        }
    }
}