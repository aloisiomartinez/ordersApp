package com.example.kingburguer.compose.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kingburguer.R
import com.example.kingburguer.common.formatted
import com.example.kingburguer.data.ProfileResponse
import com.example.kingburguer.ui.theme.KingBurguerTheme
import com.example.kingburguer.validation.Mask
import com.example.kingburguer.viewmodels.ProfileViewModel
import java.util.Date


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ProfileScreen(
    modifier: Modifier,
    viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.factory)
) {
    val state = viewModel.uiState.collectAsState().value
    ProfileScreen(modifier, state)
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileUiState
) {
    when {
        state.isLoading -> {
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(state.error, color = MaterialTheme.colors.primary)
            }
        }

        state.profile != null -> {
            Box(
                contentAlignment = Alignment.TopStart
            ) {
                Surface(
                    modifier = modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 16.dp)
                            .background(MaterialTheme.colors.background)
                    ) {
                        with(state.profile) {
                            ProfileProperty(R.string.prop_id, id)
                            ProfileProperty(R.string.prop_name, name)
                            ProfileProperty(R.string.prop_email, email)
                            ProfileProperty(R.string.prop_document, Mask("###.###.###-##", "", document))
                            ProfileProperty(R.string.prop_birthday, birthday.formatted())
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun ProfileProperty(@StringRes key: Int, value: Any) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            stringResource(key),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.onSurface
        )
        Text(
            value.toString(),
            color = Color.DarkGray
        )
    }
    Divider(modifier = Modifier.padding(vertical = 14.dp), thickness = 0.8.dp)
}

@Preview(showBackground = true)
@Composable
fun LightProfileScreenPreview() {
    KingBurguerTheme(
        darkTheme = false
    ) {
        val state = ProfileUiState(
            profile = ProfileResponse(
                id = 0,
                name = "Aloisio",
                email = "user@gmail.com",
                document = "111.222.333-11",
                birthday = Date()
            )
        )
        ProfileScreen(
            modifier = Modifier,
            state = state
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DarkProfileScreenPreview() {
    KingBurguerTheme(
        darkTheme = true
    ) {
        val state = ProfileUiState(
            profile = ProfileResponse(
                id = 0,
                name = "Aloisio",
                email = "user@gmail.com",
                document = "111.222.333-11",
                birthday = Date()
            )
        )
        ProfileScreen(
            modifier = Modifier,
            state = state
        )
    }
}