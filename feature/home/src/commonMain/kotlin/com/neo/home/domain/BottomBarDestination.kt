package com.neo.home.domain

import com.neo.shared.navigation.Screen
import nutrisport.shared.generated.resources.Res
import nutrisport.shared.generated.resources.grid
import nutrisport.shared.generated.resources.home
import nutrisport.shared.generated.resources.shopping_cart
import org.jetbrains.compose.resources.DrawableResource

enum class BottomBarDestination(
    val icon: DrawableResource,
    val title: String,
    val screen: Screen
) {
    ProductsOverview(
        icon = Res.drawable.home,
        title = "Nutri Sport",
        screen = Screen.ProductsOverview
    ),

    Cart(
        icon = Res.drawable.shopping_cart,
        title = "Cart",
        screen = Screen.Cart
    ),

    Categories(
        icon = Res.drawable.grid,
        title = "Categories",
        screen = Screen.Categories
    )


}