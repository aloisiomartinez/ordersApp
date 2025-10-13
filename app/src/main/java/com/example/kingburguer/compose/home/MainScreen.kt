package com.example.kingburguer.compose.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.kingburguer.R
import com.example.kingburguer.compose.Screen
import com.example.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun MainScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {},
            bottomBar = {
                MainBottomNavigation()
            }
        ) { contentPadding ->
            MainContentScreen(contentPadding)
        }
    }
}


@Composable
fun MainContentScreen(contentPadding: PaddingValues) {

}

@Composable
fun MainBottomNavigation() {
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
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        navigationItems.forEach { item ->
            BottomNavigationItem(
                selected = true,
                onClick = {},
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