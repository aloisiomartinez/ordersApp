package com.example.kingburguer.compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kingburguer.R
import com.example.kingburguer.compose.coupon.CouponScreen
import com.example.kingburguer.compose.home.HomeScreen
import com.example.kingburguer.compose.product.ProductScreen
import com.example.kingburguer.compose.profile.ProfileScreen
import com.example.kingburguer.data.CategoryResponse
import com.example.kingburguer.ui.theme.KingBurguerTheme
import com.example.kingburguer.viewmodels.Coupon
import com.example.kingburguer.viewmodels.MainViewModel


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MainScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: MainViewModel = viewModel(factory = MainViewModel.factory)
) {
    val shouldQuit = viewModel.uiStorage.collectAsState().value

    if (shouldQuit) {
        viewModel.reset()
        onNavigateToLogin()
    }

    MainScreen(
        onLogoutClick = {
            viewModel.logout()
        }
    )
}

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onLogoutClick: () -> Unit
) {
    val navController = rememberNavController()
    var titleTopBarId by remember { mutableIntStateOf(R.string.menu_home) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        if (currentRoute == Screen.HOME.route) {
            titleTopBarId = R.string.menu_home
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(stringResource(titleTopBarId))

                    },
                    navigationIcon = {
                        Icon(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = stringResource(R.string.app_name),
                            tint = Color.Unspecified
                        )
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        IconButton(
                            onClick =
                                onLogoutClick
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PowerSettingsNew,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    modifier = Modifier.padding(top = 8.dp),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
//                    colors = TopAppBarDefaults.topAppBarColors(
//                        containerColor = MaterialTheme.colorScheme.primary,
//                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
//                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
//                    )
                )
            },
            bottomBar = {
                MainBottomNavigation(navController) { titleId ->
                    titleTopBarId = titleId
                }
            }
        ) { contentPadding ->
            MainContentScreen(navController, contentPadding)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MainContentScreen(
    navController: NavHostController,
    contentPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HOME.route
    ) {
        composable(Screen.HOME.route) {
            HomeScreen(
                modifier = Modifier
                    .padding(
                        top = contentPadding.calculateTopPadding(),
                        bottom = contentPadding.calculateBottomPadding()
                    )
            ) { productId ->
                navController.navigate("${Screen.PRODUCT.route}/$productId")
            }
        }

        composable(Screen.COUPON.route) {
            CouponScreen(
                modifier = Modifier
                    .padding(
                        top = contentPadding.calculateTopPadding(),
                        bottom = contentPadding.calculateBottomPadding()
                    )
            )
        }

        composable(Screen.PROFILE.route) {
            ProfileScreen(
                modifier = Modifier
                    .padding(
                        top = contentPadding.calculateTopPadding(),
                        bottom = contentPadding.calculateBottomPadding()
                    )
            )
        }

        composable(
            route = "${Screen.PRODUCT.route}/{productId}",
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                }
            )
        ) {
            ProductScreen(
                modifier = Modifier
                    .padding(
                        top = contentPadding.calculateTopPadding(),
                        bottom = contentPadding.calculateBottomPadding()
                    ),
                onCouponGenerated = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun MainBottomNavigation(
    navController: NavHostController,
    onNavigationChanged: (Int) -> Unit
) {
    val navigationItems = listOf(
        NavigationItem(
            title = R.string.menu_home,
            icon = Icons.Default.Home,
            router = Screen.HOME
        ),
        NavigationItem(
            title = R.string.menu_coupon,
            icon = Icons.Default.ShoppingCart,
            router = Screen.COUPON
        ),
        NavigationItem(
            title = R.string.menu_profile,
            icon = Icons.Default.Person,
            router = Screen.PROFILE
        )
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxWidth()
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navigationItems.forEach { item ->
            BottomNavigationItem(
                modifier = Modifier.padding(16.dp),
                selected = currentRoute == item.router.route,
                onClick = {
                    if (currentRoute != item.router.route) {
                        navController.navigate(item.router.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                        onNavigationChanged(item.title)
                    }
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = stringResource(item.title))
                },
                label = {
                    Text(stringResource(item.title))
                },
                unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                selectedContentColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}

data class NavigationItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val router: Screen
)


@RequiresApi(Build.VERSION_CODES.N)
@Preview(showBackground = true)
@Composable
fun LightMainScreenPreview() {
    KingBurguerTheme(
        darkTheme = false
    ) {
        MainScreen() {}
    }
}


@RequiresApi(Build.VERSION_CODES.N)
@Preview(showBackground = true)
@Composable
fun DarkHMainScreenPreview() {
    KingBurguerTheme(
        darkTheme = true
    ) {
        MainScreen() {}
    }
}