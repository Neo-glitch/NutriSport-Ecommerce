package com.neo.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.neo.shared.component.PrimaryButton
import com.neo.shared.component.ProfileForm
import com.neo.shared.domain.Country
import nutrisport.shared.generated.resources.Res
import nutrisport.shared.generated.resources.check
import nutrisport.shared.generated.resources.checkmark_image

@Composable
fun ProfileScreen() {
    ProfileForm(
        country = Country.Usa,
        onCountrySelect = {},
        firstName = "",
        onFirstNameChange = {},
        lastName = "",
        onLastNameChange = {},
        email = "",
        city = "",
        onCityChange = {},
        postalCode = null,
        onPostalCodeChange = {},
        address = "",
        onAddressChange = {},
        phoneNumber = "",
        onPhoneNumberChange = {}

    )
}