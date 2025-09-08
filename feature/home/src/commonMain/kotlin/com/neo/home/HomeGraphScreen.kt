package com.neo.home

import ContentWithMessageBar
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.neo.home.component.BottomBar
import com.neo.home.component.CustomDrawer
import com.neo.home.domain.BottomBarDestination
import com.neo.home.domain.CustomDrawerState
import com.neo.home.domain.isOpen
import com.neo.home.domain.opposite
import com.neo.products_overview.ProductsOverviewScreen
import com.neo.shared.Alpha
import com.neo.shared.BebasNeueFont
import com.neo.shared.FontSize
import com.neo.shared.IconPrimary
import com.neo.shared.Surface
import com.neo.shared.SurfaceLighter
import com.neo.shared.TextPrimary
import com.neo.shared.navigation.Screen
import com.neo.shared.util.getScreenWidth
import nutrisport.shared.generated.resources.Res
import nutrisport.shared.generated.resources.close
import nutrisport.shared.generated.resources.menu
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeGraphScreen(
    navigateToAuth: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToDetails: (String) -> Unit,
    navigateToAdminPanel: () -> Unit
) {

    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState()
    val selectedDestination by remember {
        derivedStateOf {
            val route = currentRoute.value?.destination?.route.toString()
            when {
                route.contains(BottomBarDestination.ProductsOverview.screen.toString()) -> BottomBarDestination.ProductsOverview
                route.contains(BottomBarDestination.Cart.screen.toString()) -> BottomBarDestination.Cart
                route.contains(BottomBarDestination.Categories.screen.toString()) -> BottomBarDestination.Categories
                else -> BottomBarDestination.ProductsOverview
            }
        }
    }

    val screenWidth = remember { getScreenWidth() }
    var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }

    val offsetValue by remember { derivedStateOf { (screenWidth / 1.5).dp } }

    val animatedBackground by animateColorAsState(
        targetValue = if (drawerState.isOpen()) SurfaceLighter else Surface
    )

    val animatedOffset by animateDpAsState(
        targetValue = if (drawerState.isOpen()) offsetValue else 0.dp
    )

    val animatedScale by animateFloatAsState(
        targetValue = if (drawerState.isOpen()) 0.9f else 1f
    )

    val animatedRadius by animateDpAsState(
        targetValue = if (drawerState.isOpen()) 20.dp else 0.dp
    )

    val viewModel = koinViewModel<HomeGraphViewModel>()
    val customer by viewModel.customer.collectAsState()
    val messageBarState = rememberMessageBarState()

    Box(
        modifier = Modifier.fillMaxSize()
            .background(animatedBackground)
            .systemBarsPadding()
    ) {
        CustomDrawer(
            customer = customer,
            onProfileClick = { navigateToProfile() },
            onBlogClick = { },
            onLocationsClick = { },
            onContactUsClick = { },
            onSignOutClick = {
                viewModel.signOut(
                    onSuccess = navigateToAuth,
                    onError = { message ->
                        messageBarState.addError(message)
                    }
                )
            },
            onAdminClick = {
                navigateToAdminPanel()
            }
        )
        Scaffold(
            modifier = Modifier.fillMaxSize()
                .clip(RoundedCornerShape(size = animatedRadius))
                .offset(x = animatedOffset)
                .scale(animatedScale)
                .shadow(
                    elevation = 20.dp, shape = RoundedCornerShape(size = animatedRadius),
                    ambientColor = Color.Black.copy(alpha = Alpha.DISABLED),
                    spotColor = Color.Black.copy(alpha = Alpha.DISABLED)
                ),
            containerColor = Surface,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        AnimatedContent(
                            targetState = selectedDestination
                        ) { destination ->
                            Text(
                                text = destination.title,
                                fontFamily = BebasNeueFont(),
                                fontSize = FontSize.LARGE,
                            )
                        }
                    },
                    navigationIcon = {
                        AnimatedContent(
                            targetState = drawerState
                        ) { drawer ->
                            if (drawer.isOpen()) {
                                IconButton(onClick = {
                                    drawerState = drawerState.opposite()
                                }) {
                                    Icon(
                                        painter = painterResource(Res.drawable.close),
                                        contentDescription = "Close icon",
                                        tint = IconPrimary
                                    )
                                }
                            } else {
                                IconButton(onClick = {
                                    drawerState = drawerState.opposite()
                                }) {
                                    Icon(
                                        painter = painterResource(Res.drawable.menu),
                                        contentDescription = "Menu icon",
                                        tint = IconPrimary
                                    )
                                }
                            }

                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Surface,
                        scrolledContainerColor = Surface,
                        navigationIconContentColor = IconPrimary,
                        titleContentColor = TextPrimary
                    )
                )
            }
        ) { padding ->
            ContentWithMessageBar(
                messageBarState = messageBarState,
                modifier = Modifier.fillMaxSize().padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
                errorMaxLines = 2,
                contentBackgroundColor = Surface
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(
                        modifier = Modifier.weight(1f),
                        navController = navController,
                        startDestination = Screen.ProductsOverview
                    ) {
                        composable<Screen.ProductsOverview> {
                            ProductsOverviewScreen { id ->
                                navigateToDetails(id)
                            }
                        }
                        composable<Screen.Cart> { }
                        composable<Screen.Categories> { }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    BottomBar(
                        modifier = Modifier.padding(all = 12.dp),
                        selected = selectedDestination,
                        onSelect = { destination ->
                            navController.navigate(destination.screen) {
                                popUpTo<Screen.ProductsOverview>() {
                                    inclusive = false
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )

                }
            }
        }
    }
}