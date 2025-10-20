package com.example.kingburguer.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kingburguer.compose.login.LoginScreen
import com.example.kingburguer.compose.signup.SignUpScreen
import com.example.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun KingBurguerApp(
    contentPadding: PaddingValues
) {
    val navController = rememberNavController()
    KingBurguerNavHost(navController = navController, contentPadding = contentPadding)

}

@Composable
fun KingBurguerNavHost(navController: NavHostController, contentPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route
    ) {
        composable(Screen.LOGIN.route) {
            LoginScreen(
                onSignUpClick = { navController.navigate(Screen.SIGNUP.route) },
                contentPadding = contentPadding,
                onNavigateToHome = {
                    navController.navigate(Screen.MAIN.route) {
                        popUpTo(Screen.MAIN.route) { inclusive = true}
                    }
                }
            )
        }
        composable(Screen.SIGNUP.route) {
            SignUpScreen(
                onNavigationClick = {
                    navController.navigateUp()
                },
                onNavigateToLogin = {
                    navController.navigateUp()
                }
            )
        }
        composable(Screen.MAIN.route) {
            MainScreen()
        }

    }
}


@Preview(showBackground = true)
@Composable
fun KingBurguerAppPreview() {
    KingBurguerTheme {
        KingBurguerApp(
            contentPadding = PaddingValues()
        )
    }
}