package com.example.kingburguer.compose.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kingburguer.compose.login.LoginScreen
import com.example.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun SignUpScreen() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {}
        ) { contentPadding ->
            SignUpContentScreen(modifier = Modifier.padding(
                top = contentPadding.calculateTopPadding()
            ))
        }
    }
}


@Composable
fun SignUpContentScreen(modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Text("Olá mundo")
    }
}

@Preview(showBackground = true)
@Composable
fun LightSignUpScreenPreview() {
    KingBurguerTheme(
        darkTheme = false
    ) {
        SignUpScreen(

        )
    }
}


@Preview(showBackground = true)
@Composable
fun DarkSignUpScreenPreview() {
    KingBurguerTheme(
        darkTheme = true
    ) {
        SignUpScreen(

        )
    }
}