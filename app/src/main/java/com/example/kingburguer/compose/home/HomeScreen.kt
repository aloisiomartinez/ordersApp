package com.example.kingburguer.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kingburguer.ui.theme.KingBurguerTheme

data class Product(
    val name: String
)
data class Category(
    val name: String,
    val products: List<Product>
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        Category("Vegetariano", listOf(
            Product("Combo V1"),
            Product("Combo V2"),
            Product("Combo V3")
            )
        ),
        Category("Bovino", listOf(
            Product("Combo B1"),
            Product("Combo B2"),
            Product("Combo B3"),
            Product("Combo B4"),
            Product("Combo B5"),
            Product("Combo B6")
            )
        ),
        Category("Sobremesa", listOf(
            Product("Combo S1"),
            Product("Combo S2"),
            Product("Combo S3")
            )
        )
    )


    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.background(Color.Red),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) {cat ->
                Text(
                    text = cat.name,
                    style = MaterialTheme.typography.headlineLarge
                )

                LazyRow {
                    items(cat.products) { product ->
                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            text = product.name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
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