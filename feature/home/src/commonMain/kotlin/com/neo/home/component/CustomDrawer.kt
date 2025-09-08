package com.neo.home.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.neo.home.domain.DrawerItem
import com.neo.shared.BebasNeueFont
import com.neo.shared.FontSize
import com.neo.shared.TextPrimary
import com.neo.shared.TextSecondary
import com.neo.shared.domain.Customer
import com.neo.shared.util.RequestState

@Composable
fun CustomDrawer(
    onProfileClick: () -> Unit,
    onBlogClick: () -> Unit,
    onLocationsClick: () -> Unit,
    onContactUsClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onAdminClick: () -> Unit,
    customer: RequestState<Customer>
) {
    Column(
        modifier = Modifier.fillMaxWidth(0.6f).fillMaxHeight()
            .padding(
                horizontal = 12.dp
            ),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "NUTRISPORT",
            textAlign = TextAlign.Center,
            color = TextSecondary,
            fontFamily = BebasNeueFont(),
            fontSize = FontSize.EXTRA_LARGE
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Healthy Lifestyle",
            textAlign = TextAlign.Center,
            color = TextPrimary,
            fontSize = FontSize.REGULAR
        )
        Spacer(modifier = Modifier.height(50.dp))
        DrawerItem.entries.take(5).forEach { item ->
            DrawerItemCard(
                drawerItem = item,
                onClick = {
                    when (item) {
                        DrawerItem.Profile -> onProfileClick()
                        DrawerItem.Contact -> onContactUsClick()
                        DrawerItem.SignOut -> onSignOutClick()
                        else -> {}
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
        AnimatedContent(targetState = customer) { customerState ->
            if (customerState.isSuccess() && customerState.getSuccessData().isAdmin) {
                DrawerItemCard(
                    drawerItem = DrawerItem.Admin,
                    onClick = onAdminClick
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}