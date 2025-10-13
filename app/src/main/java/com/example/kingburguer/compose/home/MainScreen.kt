package com.example.kingburguer.compose.home

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kingburguer.R
import com.example.kingburguer.compose.Screen
import com.example.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {},
            bottomBar = {
                MainBottomNavigation(navController)
            }
        ) { contentPadding ->
            MainContentScreen(navController,contentPadding)
        }
    }
}


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
            )
        }
        composable(Screen.COUPON.route) {
            Couponcreen(
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
    }
}

@Composable
fun MainBottomNavigation(
    navController: NavHostController
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



@Composable
fun HomeScreen(
    modifier: Modifier
) {
   Surface(
       modifier = modifier.fillMaxSize()
   ) {
       Text("HOME SCREEN", style = MaterialTheme.typography.headlineLarge)
   }
}

@Composable
fun Couponcreen(
    modifier: Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Text("COUPON SCREEN", style = MaterialTheme.typography.headlineLarge)
    }
}

@Composable
fun ProfileScreen(
    modifier: Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Text("PROFILE SCREEN", style = MaterialTheme.typography.headlineLarge)
    }
}


@Preview(showBackground = true)
@Composable
fun LightMainScreenPreview() {
    KingBurguerTheme(
        darkTheme = false
    ) {
        MainScreen(

        )
    }
}



@Preview(showBackground = true)
@Composable
fun DarkHMainScreenPreview() {
    KingBurguerTheme(
        darkTheme = true
    ) {
        MainScreen(

        )
    }
}