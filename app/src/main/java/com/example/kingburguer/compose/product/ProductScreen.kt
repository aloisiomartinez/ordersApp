package com.example.kingburguer.compose.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kingburguer.R
import com.example.kingburguer.common.currency
import com.example.kingburguer.compose.component.KingButton
import com.example.kingburguer.compose.home.Product
import com.example.kingburguer.ui.theme.KingBurguerTheme
import com.example.kingburguer.viewmodels.ProductViewModel

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = viewModel(factory = ProductViewModel.factory)
) {
    ProductScreen(modifier, viewModel.product)

}

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    product: Product
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                    painter = painterResource(product.picture),
                    contentScale = ContentScale.Crop,
                    contentDescription = product.name
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = product.name,
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp),
                        text = product.price.currency(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.surface,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, bottom = 56.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    text = "Teste grande de Descrição do Produto",
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            KingButton(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = stringResource(R.string.get_coupon)
            ) { }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LightProductScreenPreview() {
    KingBurguerTheme(
        darkTheme = false
    ) {
        ProductScreen(product = Product(id = 1, name = "Teste"))
    }
}



@Preview(showBackground = true)
@Composable
fun DarkProductScreenPreview() {
    KingBurguerTheme(
        darkTheme = true
    ) {
        ProductScreen(product = Product(id = 1, name = "Teste"))
    }
}