package com.example.kingburguer.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kingburguer.compose.login.LoginScreen
import com.example.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun KingBurguerApp() {
    val navController = rememberNavController()
    KingBurguerNavHost(navController = navController)

}

@Composable
fun KingBurguerNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.LOGIN.route
    ) {
        composable(Screen.LOGIN.route) {
            LoginScreen()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun KingBurguerAppPreview() {
    KingBurguerTheme {
        KingBurguerApp()
    }
}