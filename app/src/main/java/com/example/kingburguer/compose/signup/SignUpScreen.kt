package com.example.kingburguer.compose.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kingburguer.R
import com.example.kingburguer.compose.login.component.KingAlert
import com.example.kingburguer.compose.login.component.KingButton
import com.example.kingburguer.compose.login.component.KingTextField
import com.example.kingburguer.compose.login.component.KingTextTitle
import com.example.kingburguer.ui.theme.KingBurguerTheme
import com.example.kingburguer.viewmodels.SignUpViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = viewModel(),
    onNavigateToHome: () -> Unit,
    onNavigationClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(id = R.string.login),
                            modifier = Modifier.padding(top = 16.dp)
                        )},
                    navigationIcon = {
                        IconButton(
                            onClick = onNavigationClick
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.back))
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )

                )

            }
        ) { contentPadding ->

            SignUpContentScreen(
                modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
                viewModel = viewModel,
                onNavigateToHome = onNavigateToHome
            )
        }
    }
}


@Composable
private fun SignUpContentScreen(
    modifier: Modifier,
    onNavigateToHome: () -> Unit,
    viewModel: SignUpViewModel
) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        val scrollState = rememberScrollState()
        var passwordHidden by remember { mutableStateOf(true) }

        val uiState by viewModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LaunchedEffect(key1 = uiState.goToHome) {
                    if (uiState.goToHome) {
                        onNavigateToHome()
                        viewModel.reset()
                    }
                }



                uiState.error?.let { messageError ->
                    KingAlert(
                        dialogTitle = stringResource(id = R.string.app_name),
                        dialogText = messageError,
                        onDismissRequest = {},
                        onConfirmation = {
                            viewModel.reset()
                        },
                        icon = Icons.Filled.Info
                    )
                }

                KingTextTitle(text = stringResource(R.string.signup))

                KingTextField(
                    value = viewModel.email,
                    label = R.string.email,
                    placeholder = R.string.hint_email,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ) {

                }

                KingTextField(
                    value = viewModel.name,
                    label = R.string.name,
                    placeholder = R.string.hint_name,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ) {

                }

                KingTextField(
                    value = viewModel.password,
                    label = R.string.password,
                    placeholder = R.string.hint_password,
                    keyboardType = KeyboardType.Password,
                    ofuscate = passwordHidden,
                    imeAction = ImeAction.Next,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordHidden = !passwordHidden
                            }
                        ) {
                            val image = if (passwordHidden) {
                                Icons.Filled.VisibilityOff
                            } else {
                                Icons.Filled.Visibility
                            }

                            val description = if (passwordHidden) {
                                stringResource(id = R.string.show_password)
                            } else {
                                stringResource(id = R.string.hide_password)
                            }

                            Icon(
                                imageVector = image,
                                contentDescription = description
                            )
                        }

                    }
                ) {

                }

                KingTextField(
                    value = viewModel.confirmPassword,
                    label = R.string.confirm_password,
                    placeholder = R.string.hint_confirm_password,
                    keyboardType = KeyboardType.Password,
                    ofuscate = passwordHidden,
                    imeAction = ImeAction.Next,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordHidden = !passwordHidden
                            }
                        ) {
                            val image = if (passwordHidden) {
                                Icons.Filled.VisibilityOff
                            } else {
                                Icons.Filled.Visibility
                            }

                            val description = if (passwordHidden) {
                                stringResource(id = R.string.show_password)
                            } else {
                                stringResource(id = R.string.hide_password)
                            }

                            Icon(
                                imageVector = image,
                                contentDescription = description
                            )
                        }

                    }
                ) {

                }

                KingTextField(
                    value = viewModel.document,
                    label = R.string.document,
                    placeholder = R.string.hint_email,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ) {

                }

                KingTextField(
                    value = viewModel.birthday,
                    label = R.string.birthday,
                    placeholder = R.string.hint_birthday,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ) {

                }


                KingButton(
                    text = stringResource(id = R.string.sign_up),
                    enabled = true,
                    loading = uiState.isLoading
                ) {
                    viewModel.send()
                }

            }

            Image(
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.BottomCenter,
                painter = painterResource(R.drawable.cover3),
                contentDescription = stringResource(id = R.string.hamburguer)
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LightSignUpScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = false) {
        SignUpScreen(
            onNavigateToHome = {},
            onNavigationClick = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DarkSignUpScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = true) {
        SignUpScreen(
            onNavigateToHome = {},
            onNavigationClick = {}
        )
    }
}
