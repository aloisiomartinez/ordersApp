package com.example.kingburguer.compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kingburguer.compose.login.LoginScreen
import com.example.kingburguer.compose.signup.SignUpScreen
import com.example.kingburguer.ui.theme.KingBurguerTheme

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun KingBurguerApp(
    startDestination: Screen,
    contentPadding: PaddingValues
) {
    val navController = rememberNavController()

    KingBurguerNavHost(navController, contentPadding, startDestination)


}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun KingBurguerNavHost(navController: NavHostController, contentPadding: PaddingValues, startDestination: Screen) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
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
            MainScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.LOGIN.route) {
                        popUpTo(Screen.MAIN.route) { inclusive = true }
                    }
                }
            )
        }

    }
}


@RequiresApi(Build.VERSION_CODES.N)
@Preview(showBackground = true)
@Composable
fun KingBurguerAppPreview() {
    KingBurguerTheme {
        KingBurguerApp(
            startDestination = Screen.LOGIN,
            contentPadding = PaddingValues()
        )
    }
}