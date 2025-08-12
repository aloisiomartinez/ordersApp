package com.example.kingburguer.compose.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val goToHome: Boolean = false,
    val error: String? = null
)