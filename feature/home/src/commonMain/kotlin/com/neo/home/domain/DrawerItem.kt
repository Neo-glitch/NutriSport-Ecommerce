package com.neo.home.domain

import nutrisport.shared.generated.resources.Res
import nutrisport.shared.generated.resources.book
import nutrisport.shared.generated.resources.edit
import nutrisport.shared.generated.resources.log_out
import nutrisport.shared.generated.resources.map_pin
import nutrisport.shared.generated.resources.unlock
import nutrisport.shared.generated.resources.user
import org.jetbrains.compose.resources.DrawableResource

enum class DrawerItem(
    val title: String,
    val icon: DrawableResource
) {
    Profile(
        title = "Profile",
        icon = Res.drawable.user
    ),
    Blog(
        title = "Blog",
        icon = Res.drawable.book
    ),
    Locations(
        title = "Locations",
        icon = Res.drawable.map_pin
    ),
    Contact(
        title = "Contact Us",
        icon = Res.drawable.edit
    ),
    SignOut(
        title = "Sign Out",
        icon = Res.drawable.log_out
    ),
    Admin(
        title = "Admin Panel",
        icon = Res.drawable.unlock
    )
}