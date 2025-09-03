package com.example.kingburguer.compose.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun HomeScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {}
        ) { contentPadding ->
            HomeContentScreen(modifier = Modifier.padding(top = contentPadding.calculateTopPadding() ))
        }
    }
}

@Composable
fun HomeContentScreen(
    modifier: Modifier
) {
   Surface(
       modifier = modifier.fillMaxSize()
   ) {
       Text("HOME SCREEN")
   }
}


@Preview(showBackground = true)
@Composable
fun LightHomeScreenPreview() {
    KingBurguerTheme(
        darkTheme = false
    ) {
        HomeScreen(

        )
    }
}



@Preview(showBackground = true)
@Composable
fun DarkHomeScreenPreview() {
    KingBurguerTheme(
        darkTheme = true
    ) {
        HomeScreen(

        )
    }
}