package com.example.kingburguer.compose.coupon

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kingburguer.compose.MainScreen
import com.example.kingburguer.ui.theme.KingBurguerTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.kingburguer.R
import com.example.kingburguer.ui.theme.Orange600
import com.example.kingburguer.viewmodels.CouponFilter
import com.example.kingburguer.viewmodels.CouponViewModel
import com.example.kingburguer.viewmodels.HomeViewModel
import com.example.kingburguer.viewmodels.LoginViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class Coupon(
    val id: Int,
    val productId: Int,
    val coupon: String,
    val createdAt: String,
    val expirationAt: String,
)


@Composable
fun CouponScreen(
    modifier: Modifier = Modifier,
    viewModel: CouponViewModel = viewModel(factory = CouponViewModel.factory),
) {
    val coupons = viewModel.uiState.collectAsState().value
    CouponScreen(state = coupons)

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CouponScreen(
    modifier: Modifier = Modifier,
    state: CouponUiState
) {

    Log.i("CouponScreen", "state: $state")

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
        when {
            state.isLoading -> {
                androidx.compose.material3.CircularProgressIndicator()
            }

            state.error != null -> {
                Text(state.error, color = androidx.compose.material3.MaterialTheme.colorScheme.primary)
            }

            state.coupons == null -> {
                Log.i("CouponScreen", "State NULOOOOOOOOO: $state")

                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text("Nenhum Cupom Disponível", modifier = Modifier.padding(16.dp) )
                }
            }

            state.coupons != null -> {

            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

        val options = listOf("Ativos", "Expirados", "Todos")
        var selectedOption by remember { mutableStateOf(options.first()) }

        Row(
            modifier = Modifier.padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            options.forEach { text ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { selectedOption = text }
                    )
                    Text(
                        text = text,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .padding(start = 0.dp)
                    )
                }
            }
        }
        @Composable
        fun CouponScreen() {

        }

//        LazyColumn {
//            itemsIndexed(coupons) { index, item ->
//                val expired = toDate(item.expirationAt).before(Date())
//                if (selectedOption == options[0] &&  expired) return@itemsIndexed
//                if (selectedOption == options[1] && !expired) return@itemsIndexed
//
//                val topPadding = if (index == 0) 12.dp else 20.dp
//                val bottomPadding = if (index == coupons.size - 1) 12.dp else 0.dp
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(
//                            start = 12.dp,
//                            end = 20.dp,
//                            bottom = bottomPadding,
//                            top = topPadding
//                        ),
//                ) {
//                    Column(
//                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp),
//                    ) {
//                        Text(
//                            text = item.coupon,
//                            color = if (expired) Color.Gray else MaterialTheme.colors.primary,
//                            style = MaterialTheme.typography.h5,
//                            fontWeight = FontWeight.Bold,
//                        )
//
//                        Text(
//                            text = "Expira: ${toDateBR(item.expirationAt)}",
//                            color = MaterialTheme.colors.onSurface,
 //                           style = MaterialTheme.typography.body2,
//                        )
//                    }
//                }
//            }
//        }
    }

}

// TODO: mover para outro lugar, por hora fica aqui para testar layout
private fun toDate(value: String): Date {
    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        .parse(value)
    return date ?: Date()
}

// TODO: mover para outro lugar, por hora fica aqui para testar layout
private fun toDateBR(value: String): String {
    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        .parse(value)

    val dateFormatted = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        .format(date!!) // unwrap

    return dateFormatted
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LightCouponScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = false) {
        CouponScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DarkCouponScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = true) {
        CouponScreen()
    }
}