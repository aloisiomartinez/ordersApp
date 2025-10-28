package com.example.kingburguer.compose.coupon

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kingburguer.compose.MainScreen
import com.example.kingburguer.ui.theme.KingBurguerTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kingburguer.viewmodels.CouponFilter
import com.example.kingburguer.viewmodels.CouponViewModel
import com.example.kingburguer.viewmodels.LoginViewModel


@Composable
fun CouponScreen(
    modifier: Modifier = Modifier,
    viewModel: CouponViewModel = viewModel(factory = CouponViewModel.factory)
) {
    val filteredCoupons by viewModel.filteredCoupons.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val options = listOf("Ativos", "Expirados", "Todos") // lista de opções
    var selectedOption by remember { mutableStateOf(options.first()) }

    Surface(
        modifier = modifier.fillMaxSize().background(Color(0xFFF8F8F8)).padding(32.dp)
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (filteredCoupons.isEmpty()) {
            Text(
                text = "Nenhum cupom encontrado",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(16.dp)
            )
        } else {

        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                elevation = 2.dp,
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.White,
                border = BorderStroke(1.dp, Color(0xFFE0E0E0))
            ) {
                Row(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = selectedFilter == CouponFilter.ACTIVE,
                                onClick = { viewModel.setFilter(CouponFilter.ACTIVE) }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedFilter == CouponFilter.ACTIVE,
                            onClick = { viewModel.setFilter(CouponFilter.ACTIVE) }
                        )
                        Text("Ativos",
                            modifier = Modifier
                            .padding(start = 0.dp))
                    }

                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = selectedFilter == CouponFilter.EXPIRED,
                                onClick = { viewModel.setFilter(CouponFilter.EXPIRED) }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedFilter == CouponFilter.EXPIRED,
                            onClick = { viewModel.setFilter(CouponFilter.EXPIRED) }
                        )
                        Text("Expirados",
                            modifier = Modifier
                                .padding(start = 0.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = selectedFilter == CouponFilter.ALL,
                                onClick = { viewModel.setFilter(CouponFilter.ALL) }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedFilter == CouponFilter.ALL,
                            onClick = { viewModel.setFilter(CouponFilter.ALL) }
                        )
                        Text("Todos",
                            Modifier
                                .padding(start = 0.dp)
                        )
                    }
                }

            }

                        Text(
                            text = "Cupons disponíveis",
                            style = MaterialTheme.typography.subtitle1,
                            color = MaterialTheme.colors.secondary,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            itemsIndexed(filteredCoupons) { _, coupon ->
                                CouponCard(
                                    coupon.name,
                                    coupon.expirationDate
                                )
                            }
                        }
                }

            }


}





@Composable
fun CouponCard(name: String, expiration: String) {
    Card(
        modifier = Modifier
            .background(color = Color.Blue)
            .fillMaxWidth()
            .border(BorderStroke(4.dp, MaterialTheme.colors.onSurface))
    ) {
       Column(
           modifier = Modifier.padding(8.dp).background(color = MaterialTheme.colors.onPrimary)
       ) {
           Text(
               text = name,
               modifier = Modifier.padding(bottom = 8.dp)
           )
           Text(
               text = expiration
           )
       }
    }
}


@Preview(showBackground = true)
@Composable
fun LightCouponScreenPreview() {
    KingBurguerTheme(
        darkTheme = false
    ) {
        CouponScreen(
            viewModel =  viewModel(factory = CouponViewModel.factory)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun DarkCouponScreenPreview() {
    KingBurguerTheme(
        darkTheme = true
    ) {
        CouponScreen(
            viewModel =  viewModel(factory = CouponViewModel.factory)
        )
    }
}