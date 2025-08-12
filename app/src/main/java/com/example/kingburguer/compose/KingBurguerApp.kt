package com.example.kingburguer.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kingburguer.compose.login.LoginScreen
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
        startDestination = Screen.LOGIN.route
    ) {
        composable(Screen.LOGIN.route) {
            LoginScreen(contentPadding)
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