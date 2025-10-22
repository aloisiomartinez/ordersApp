package com.example.kingburguer.compose.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kingburguer.R
import com.example.kingburguer.common.currency
import com.example.kingburguer.ui.theme.KingBurguerTheme

data class Product(
    val name: String,
    @DrawableRes val picture: Int = R.drawable.example,
    val price: Double = 19.0
)
data class Category(
    val name: String,
    val products: List<Product>
)

@OptIn(ExperimentalFoundationApi::class)
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
            Product("Combo B1 com nom super grande e que não cabe na tela"),
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
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {


        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(categories) {index, cat ->
                val topPadding = if (index == 0) 20.dp else 0.dp
                val bottomPadding = if(index == categories.size - 1) 20.dp else 0.dp

                Text(
                    modifier = Modifier
                        .padding(start = 12.dp, bottom = 12.dp, top = topPadding),
                    text = cat.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineMedium
                )

                CompositionLocalProvider(
                    LocalOverscrollConfiguration provides null
                ) {
                    LazyRow(
                        modifier = Modifier.padding(bottom = bottomPadding)
                    ) {
                        itemsIndexed(cat.products) { index, product ->
                            val startPadding = if (index == 0) 20.dp else 8.dp
                            val endPadding = if(index == categories.size - 1) 20.dp else 8.dp

                            Column(
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(start = startPadding, end = endPadding)
                                    .widthIn(max = 160.dp)
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(140.dp, 180.dp)
                                        .border(
                                            BorderStroke(0.3.dp, Color.Gray),
                                            RoundedCornerShape(8.dp)
                                        ),
                                    painter = painterResource(product.picture),
                                    contentDescription = product.name
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = MaterialTheme.colorScheme.inverseSurface,
                                    text = product.name,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            MaterialTheme.colorScheme.primary,
                                            RoundedCornerShape(4.dp)
                                        ),
                                    color = MaterialTheme.colorScheme.surface,
                                    text = product.price.currency(),
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                        }
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