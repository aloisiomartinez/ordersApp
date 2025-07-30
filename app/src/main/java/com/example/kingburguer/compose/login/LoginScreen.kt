package com.example.kingburguer.compose.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun LoginScreen() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Olá")
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    KingBurguerTheme {
        LoginScreen()
    }
}