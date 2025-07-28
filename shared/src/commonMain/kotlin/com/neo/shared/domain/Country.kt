package com.neo.shared.domain

import com.neo.shared.Resources
import nutrisport.shared.generated.resources.Res
import nutrisport.shared.generated.resources.india
import nutrisport.shared.generated.resources.serbia
import nutrisport.shared.generated.resources.usa
import org.jetbrains.compose.resources.DrawableResource

enum class Country(
    val dialCode: Int,
    val code: String,
    val flag: DrawableResource
) {
    Serbia(
        dialCode = 381,
        code = "RS",
        flag = Res.drawable.serbia
    ),
    India(
        dialCode = 91,
        code = "IN",
        flag = Res.drawable.india
    ),
    Usa(
        dialCode = 1,
        code = "US",
        flag = Res.drawable.usa
    )
}