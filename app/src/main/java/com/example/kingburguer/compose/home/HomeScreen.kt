package com.example.kingburguer.compose.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.kingburguer.R
import com.example.kingburguer.common.currency
import com.example.kingburguer.data.CategoryResponse
import com.example.kingburguer.data.HighlightProductResponse
import com.example.kingburguer.ui.theme.KingBurguerTheme
import com.example.kingburguer.ui.theme.Orange600
import com.example.kingburguer.viewmodels.HomeViewModel
import org.jetbrains.annotations.Async
import java.sql.Date


@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.factory),
    onProductClicked: (Int) -> Unit
) {
    val state = viewModel.uiState.collectAsState().value
    HomeScreen(
        modifier, state, onProductClicked
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, state: HomeUiState, onProductClicked: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        HighlightView(state.highlightUiState, onProductClicked)
        CategoriesView( state.categoryUiState, onProductClicked)
        
    }
}

@Composable
private fun HighlightView(state: HighlightUiState, onProductClicked: (Int) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text(state.error, color = MaterialTheme.colorScheme.primary)
            }

            state.product != null -> {
                Box(
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(230.dp),
                        placeholder = painterResource(R.drawable.logo),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        model = state.product.pictureUrl
                    )

                    OutlinedButton(
                        onClick = {
                            onProductClicked(state.product.productId)
                        },
                        modifier = Modifier.padding(12.dp),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 6.dp
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = Orange600
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.show_more), color = Color.White
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun CategoriesView(
     state: CategoryUiState, onProductClicked: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text(state.error, color = MaterialTheme.colorScheme.primary)
            }

            else -> {
                HomeScreenColumn(Modifier, state.categories, onProductClicked)
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenColumn(
    modifier: Modifier = Modifier,
    categories: List<CategoryResponse>,
    onProductClicked: (Int) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {


        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(categories) { index, cat ->
                val topPadding = if (index == 0) 20.dp else 0.dp
                val bottomPadding = if (index == categories.size - 1) 20.dp else 0.dp

                Text(
                    modifier = Modifier.padding(start = 12.dp, bottom = 12.dp, top = topPadding),
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
                            val endPadding = if (index == categories.size - 1) 20.dp else 8.dp

                            Column(
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(start = startPadding, end = endPadding)
                                    .widthIn(max = 160.dp)
                            ) {
                                AsyncImage(
                                    model = product.pictureUrl,
                                    placeholder = painterResource(R.drawable.logo),
                                    modifier = Modifier
                                        .size(140.dp, 180.dp)
                                        .border(
                                            BorderStroke(0.3.dp, Color.Gray),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            onProductClicked(product.id)
                                        },
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
        val state = HomeUiState(
            categoryUiState = CategoryUiState(isLoading = true)
        )
        HomeScreen(state = state) {}
    }
}


@Preview(showBackground = true)
@Composable
fun DarkHomeErrorScreenPreview() {
    KingBurguerTheme(
        darkTheme = true
    ) {
        val state = HomeUiState(
            categoryUiState = CategoryUiState(error = "Erro de teste")
        )
        HomeScreen(Modifier, state = state) {}
    }
}


@Preview(showBackground = true)
@Composable
fun DarkHomeEmptyScreenPreview() {
    KingBurguerTheme(
        darkTheme = true
    ) {
        val state = HomeUiState(
            categoryUiState = CategoryUiState(
                categories = emptyList()
            ),
        )
        HomeScreen(state = state) {}
    }
}

@Preview(showBackground = true)
@Composable
fun LightHighlightSuccessPreview() {
    KingBurguerTheme(
        dynamicColor = false, darkTheme = false
    ) {
        val state = HomeUiState(
            highlightUiState = HighlightUiState(
                product = HighlightProductResponse(
                    0, 0, "https://placehold.co/600x400", java.util.Date()
                )
            )
        )

        HomeScreen(state = state) {}
    }
}