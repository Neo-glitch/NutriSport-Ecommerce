package com.neo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.neo.admin_panel.AdminPanelScreen
import com.neo.auth.AuthScreen
import com.neo.category_search.CategorySearchScreen
import com.neo.checkout.CheckoutScreen
import com.neo.details.DetailsScreen
import com.neo.home.HomeGraphScreen
import com.neo.manage_product.ManageProductScreen
import com.neo.payment_completed.PaymentCompleted
import com.neo.profile.ProfileScreen
import com.neo.shared.domain.ProductCategory
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
                },
                navigateToAdminPanel = {
                    navController.navigate(Screen.AdminPanel)
                },
                navigateToDetails = {
                    navController.navigate(Screen.Details(it))
                },
                navigateToCategorySearch = {
                    navController.navigate(Screen.CategorySearch(it))
                },
                navigateToCheckout = { totalAmount ->
                    navController.navigate(Screen.Checkout(totalAmount))
                }
            )
        }

        composable<Screen.Details> {
            DetailsScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Screen.CategorySearch> {
            val category = ProductCategory.valueOf(it.toRoute<Screen.CategorySearch>().category)
            CategorySearchScreen(
                category = category,
                navigateToDetails = { id ->
                    navController.navigate(Screen.Details(id))
                },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Screen.Profile> {
            ProfileScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Screen.AdminPanel> {
            AdminPanelScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToManageProduct = { id ->
                    navController.navigate(Screen.ManageProduct(id = id))
                }
            )
        }

        composable<Screen.ManageProduct> {
            val id = it.toRoute<Screen.ManageProduct>().id
            ManageProductScreen(
                id = id,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Screen.Checkout> {
            val totalAmount = it.toRoute<Screen.Checkout>().totalAmount
            CheckoutScreen(
                totalAmount = totalAmount.toDoubleOrNull() ?: 0.0,
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToPaymentCompleted = { isSuccess, error ->
                    navController.navigate(Screen.PaymentCompleted(isSuccess, error))
                }
            )
        }
        composable<Screen.PaymentCompleted> {
            PaymentCompleted(
                navigateBack = {
                    navController.navigate(Screen.HomeGraph) {
                        launchSingleTop = true
                        // Clear backstack completely
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

    }
}