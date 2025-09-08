package com.example.kingburguer.compose.signup

data class SignUpUiState(
    val isLoading: Boolean = false,
    val goToHome: Boolean = false,
    val error: String? = null
)